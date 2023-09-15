package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.RoleDto;
import com.waff.gameverse_backend.model.Role;
import com.waff.gameverse_backend.model.User;
import com.waff.gameverse_backend.repository.RoleRepository;
import com.waff.gameverse_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;


@Service
public class RoleService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository,UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }


    public Role findById(Long id) {
        return this.roleRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Role mit der gegeben Id existiert nicht"));
    }

    public Set<Role> findAllByIds(Iterable<Long> ids) {
        return new HashSet<>(this.roleRepository.findAllById(ids));
    }

    public Set<Role> findAll() {
        return new HashSet<>(this.roleRepository.findAll());
    }

    public Role findByName(String name) {
        return this.roleRepository.findByName(name).orElseThrow(() -> new NoSuchElementException("Role mit dem gegeben Namen existiert nicht!"));
    }


    public Role save(String name) {

        var toCheck = this.roleRepository.findByName(name);

        if(toCheck.isEmpty()) {

            Role toSave = new Role();
            toSave.setName(name);
            toSave.setPrivileges(new HashSet<>());
            toSave.setUsers(new HashSet<>());
            return this.roleRepository.save(toSave);

        } else {
            throw new IllegalArgumentException("Der angegebene Name wird schon von einem Role genutzt!");
        }
    }

    public Role update(RoleDto roleDto) {

        var toUpdate = this.roleRepository.findById(roleDto.getId())
            .orElseThrow(() -> new NoSuchElementException("Role mit der gegebenen Id existiert nicht!"));

        if(roleDto.getName().isEmpty()) { throw new IllegalArgumentException("Der Name des Roles darf nicht leer sein!"); }

        toUpdate.setName(roleDto.getName());
        return this.roleRepository.save(toUpdate);

    }

    public Role delete(Long id) {

        var toDelete = this.roleRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Role mit der gegebenen Id existiert nicht!"));

        var users = toDelete.getUsers();
        for(User user : users) {
            user.setRole(null);
            this.userRepository.save(user);
        }

        toDelete.setUsers(new HashSet<>());
        toDelete.setPrivileges(new HashSet<>());
        this.roleRepository.save(toDelete);

        this.roleRepository.delete(toDelete);
        return toDelete;

    }

}
