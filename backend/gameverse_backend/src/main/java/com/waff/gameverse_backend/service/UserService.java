package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.UserDto;
import com.waff.gameverse_backend.model.Role;
import com.waff.gameverse_backend.model.User;
import com.waff.gameverse_backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User mit der gegeben Id existiert nicht"));
    }

    public Set<User> findAllByIds(Iterable<Long> ids) {
        return new HashSet<>(this.userRepository.findAllById(ids));
    }

    public Set<User> findAll() {
        return new HashSet<>(this.userRepository.findAll());
    }

    public User findByUsername(String name) {
        return this.userRepository.findByUsername(name).orElseThrow(() -> new NoSuchElementException("User mit dem gegeben Namen existiert nicht!"));
    }


    public User save(UserDto userDto) {

        var toCheck = this.userRepository.findByUsername(userDto.getUsername());

        if(toCheck.isEmpty()) {

            User toSave = new User(userDto);
            return this.userRepository.save(toSave);

        } else {
            throw new IllegalArgumentException("Der angegebene Name wird schon von einem User genutzt!");
        }
    }

    public User update(UserDto userDto) {

        var toUpdate = this.userRepository.findById(userDto.getId())
            .orElseThrow(() -> new NoSuchElementException("User mit der gegebenen Id existiert nicht!"));

        if(userDto.getUsername().isBlank() || userDto.getUsername() == null) { throw new IllegalArgumentException("Der Name des Users darf nicht leer sein!"); }

        toUpdate.setUsername(userDto.getUsername());
        toUpdate.setPassword(userDto.getPassword());
        toUpdate.setRole(new Role(userDto.getRole()));
        return this.userRepository.save(toUpdate);

    }

    public User delete(Long id) {

        var toDelete = this.userRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("User mit der gegebenen Id existiert nicht!"));

        this.userRepository.delete(toDelete);
        return toDelete;

    }


    /* Filter chain Methode, nicht im Normalfall nutzen! immer die Save Methode! */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User with the given name doesn't exist!"));
    }
}
