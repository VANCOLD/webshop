package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.SimpleUserDto;
import com.waff.gameverse_backend.dto.UserDto;
import com.waff.gameverse_backend.enums.Gender;
import com.waff.gameverse_backend.model.Address;
import com.waff.gameverse_backend.model.Cart;
import com.waff.gameverse_backend.model.User;
import com.waff.gameverse_backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * The UserService class provides methods for managing user-related operations,
 * including user retrieval, creation, updating, and deletion. It also implements
 * the UserDetailsService interface to support Spring Security authentication.
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    private final AddressService addressService;

    /**
     * Constructs a new UserService instance.
     *
     * @param userRepository The UserRepository for database interactions.
     */
    UserService(UserRepository userRepository, RoleService roleService, AddressService addressService) {
        this.userRepository = userRepository;
        this.roleService    = roleService;
        this.addressService = addressService;
    }

    /**
     * Finds a user by their unique identifier (ID).
     *
     * @param id The ID of the user to retrieve.
     * @return The user if found, or throws an exception if not found.
     * @throws NoSuchElementException If the user with the given ID does not exist.
     */
    public User findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User with the given ID does not exist"));
    }

    /**
     * Retrieves a list of all users.
     *
     * @return A list of all users.
     */
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    /**
     * Finds a user by their username.
     *
     * @param name The username of the user to retrieve.
     * @return The user if found, or throws an exception if not found.
     * @throws NoSuchElementException If the user with the given username does not exist.
     */
    public User findByUsername(String name) {
        return this.userRepository.findByUsername(name).orElseThrow(() -> new NoSuchElementException("User with the given username does not exist"));
    }

    /**
     * Creates a new user based on the provided UserDto.
     *
     * @param userDto The UserDto containing user information.
     * @return The newly created user.
     * @throws IllegalArgumentException If the provided username is already in use.
     */
    public User save(SimpleUserDto userDto) {
        var toCheck = this.userRepository.findByUsername(userDto.getUsername());

        if (toCheck.isEmpty()) {
            User toSave = new User(userDto);
            toSave.setRole(roleService.findByName("user"));
            return this.userRepository.save(toSave);
        } else {
            throw new IllegalArgumentException("The provided username is already in use by another user.");
        }
    }

    /**
     * Creates a new user based on the provided UserDto.
     *
     * @param userDto The UserDto containing user information.
     * @return The newly created user.
     * @throws IllegalArgumentException If the provided username is already in use.
     */
    public User save(UserDto userDto) {
        var toCheck = this.userRepository.findByUsername(userDto.getUsername());
        var role = this.roleService.findByName(userDto.getRole().getName());

        var address = addressService.exists(userDto.getAddress())
            ? this.addressService.findByAddress(userDto.getAddress())
            : this.addressService.save(userDto.getAddress());

        if (toCheck.isEmpty()) {
            User toSave = new User(userDto);
            toSave.setRole(role);
            toSave.setAddress(address);
            return this.userRepository.save(toSave);
        } else {
            throw new IllegalArgumentException("The provided username is already in use by another user.");
        }
    }

    /**
     * Updates an existing user's information based on the provided UserDto.
     *
     * @param userDto The UserDto containing updated user information.
     * @return The updated user.
     * @throws IllegalArgumentException If the provided username is blank or null.
     * @throws NoSuchElementException   If the user with the given ID does not exist.
     */
    public User update(UserDto userDto) {
        var toUpdate = this.userRepository.findById(userDto.getId())
            .orElseThrow(() -> new NoSuchElementException("User with the given ID does not exist"));

        toUpdate.setUsername(userDto.getUsername());
        toUpdate.setPassword(userDto.getPassword());
        toUpdate.setFirstname(userDto.getFirstname());
        toUpdate.setLastname(userDto.getLastname());
        toUpdate.setEmail(userDto.getEmail());
        toUpdate.setGender(Gender.valueOf(userDto.getGender()));
        toUpdate.setRole(this.roleService.findById(userDto.getRole().getId()));

        if(!addressService.exists(userDto.getAddress())) {
            var newAddress = addressService.save(userDto.getAddress());
            toUpdate.setAddress(newAddress);
        } else {
            var existingAddress = addressService.findByAddress(userDto.getAddress());
            toUpdate.setAddress(existingAddress);
        }

        return this.userRepository.save(toUpdate);
    }

    /**
     * Deletes a user based on their unique identifier (ID).
     *
     * @param id The ID of the user to delete.
     * @return The deleted user.
     * @throws NoSuchElementException If the user with the given ID does not exist.
     */
    public void delete(Long id) {
        var toDelete = this.userRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("User with the given ID does not exist"));

        toDelete.setRole(null);
        toDelete.setAddress(null);
        toDelete.setCart(null);
        toDelete.setOrders(List.of());
        this.userRepository.delete(toDelete);
    }

    /**
     * This method is used by Spring Security to load a user by their username.
     * It implements the UserDetailsService interface.
     *
     * @param username The username of the user to load.
     * @return The UserDetails object representing the loaded user.
     * @throws UsernameNotFoundException If the user with the given username does not exist.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User with the given username does not exist"));
    }


    public Cart getCart(Long userId) {

        User user = this.findById(userId);
        Cart cart = user.getCart();

        if (cart == null) {

            cart.setUser(user);
            return this.save(user.convertToDto()).getCart();

        } else {
            return cart;
        }
    }
}
