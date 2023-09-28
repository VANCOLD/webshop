package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.RoleDto;
import com.waff.gameverse_backend.model.Role;
import com.waff.gameverse_backend.model.User;
import com.waff.gameverse_backend.repository.RoleRepository;
import com.waff.gameverse_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The RoleService class provides methods for managing roles, including retrieval, creation, update, and deletion.
 * It interacts with the RoleRepository and UserRepository to perform these operations.
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    /**
     * Constructs a new RoleService instance.
     *
     * @param roleRepository The RoleRepository for managing roles.
     * @param userRepository The UserRepository for managing users.
     */
    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    /**
     * Find a role by its ID.
     *
     * @param id The ID of the role to find.
     * @return The found role.
     * @throws NoSuchElementException If no role with the given ID exists.
     */
    public Role findById(Long id) {
        return this.roleRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Role with the given ID does not exist"));
    }

    /**
     * Find all roles.
     *
     * @return A list of all roles.
     */
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }

    /**
     * Find a role by its name.
     *
     * @param name The name of the role to find.
     * @return The found role.
     * @throws NoSuchElementException If no role with the given name exists.
     */
    public Role findByName(String name) {
        return this.roleRepository.findByName(name)
            .orElseThrow(() -> new NoSuchElementException("Role with the given name does not exist"));
    }

    /**
     * Save a new role with the given name.
     *
     * @param name The name of the new role to save.
     * @return The saved role.
     * @throws IllegalArgumentException If a role with the same name already exists.
     */
    public Role save(String name) {
        var toCheck = this.roleRepository.findByName(name);
        if (toCheck.isEmpty()) {
            Role toSave = new Role();
            toSave.setName(name);
            toSave.setPrivileges(List.of());
            toSave.setUsers(List.of());
            return this.roleRepository.save(toSave);
        } else {
            throw new IllegalArgumentException("The specified name is already used by a role");
        }
    }

    /**
     * Update a role with the information from the provided RoleDto.
     *
     * @param roleDto The RoleDto containing updated role information.
     * @return The updated role.
     * @throws NoSuchElementException  If the role with the given ID does not exist.
     * @throws IllegalArgumentException If the role name is empty.
     */
    public Role update(RoleDto roleDto) {
        var toUpdate = this.roleRepository.findById(roleDto.getId())
            .orElseThrow(() -> new NoSuchElementException("Role with the given ID does not exist"));

        toUpdate.setName(roleDto.getName());
        return this.roleRepository.save(toUpdate);
    }

    /**
     * Delete a role with the given ID and update associated users.
     *
     * @param id The ID of the role to delete.
     * @return The deleted role.
     * @throws NoSuchElementException If the role with the given ID does not exist.
     */
    public Role delete(Long id) {
        var toDelete = this.roleRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Role with the given ID does not exist"));
        var users = toDelete.getUsers();
        for (User user : users) {
            user.setRole(null);
            this.userRepository.save(user);
        }
        toDelete.setUsers(new ArrayList<>());
        toDelete.setPrivileges(new ArrayList<>());
        this.roleRepository.delete(toDelete);
        return toDelete;
    }
}
