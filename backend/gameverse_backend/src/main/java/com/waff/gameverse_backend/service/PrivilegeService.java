package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.PrivilegeDto;
import com.waff.gameverse_backend.model.Privilege;
import com.waff.gameverse_backend.model.Role;
import com.waff.gameverse_backend.repository.PrivilegeRepository;
import com.waff.gameverse_backend.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.List;

/**
 * The PrivilegeService class provides methods for managing privileges, including retrieval, creation, update, and deletion.
 * It interacts with the PrivilegeRepository and RoleRepository to perform these operations.
 */
@Service
public class PrivilegeService {

    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;

    /**
     * Constructs a new PrivilegeService instance.
     *
     * @param privilegeRepository The PrivilegeRepository for managing privileges.
     * @param roleRepository      The RoleRepository for managing roles.
     */
    public PrivilegeService(PrivilegeRepository privilegeRepository, RoleRepository roleRepository) {
        this.privilegeRepository = privilegeRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Find a privilege by its ID.
     *
     * @param id The ID of the privilege to find.
     * @return The found privilege.
     * @throws NoSuchElementException If no privilege with the given ID exists.
     */
    public Privilege findById(Long id) {
        return this.privilegeRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Privilege with the given ID does not exist"));
    }

    /**
     * Find all privileges.
     *
     * @return A list of all privileges.
     */
    public List<Privilege> findAll() {
        return this.privilegeRepository.findAll();
    }

    /**
     * Find a privilege by its name.
     *
     * @param name The name of the privilege to find.
     * @return The found privilege.
     * @throws NoSuchElementException If no privilege with the given name exists.
     */
    public Privilege findByName(String name) {
        return this.privilegeRepository.findByName(name)
            .orElseThrow(() -> new NoSuchElementException("Privilege with the given name does not exist"));
    }

    /**
     * Save a new privilege with the given name.
     *
     * @param name The name of the new privilege to save.
     * @return The saved privilege.
     * @throws IllegalArgumentException If a privilege with the same name already exists.
     */
    public Privilege save(String name) {
        var toCheck = this.privilegeRepository.findByName(name);
        if (toCheck.isEmpty()) {
            Privilege toSave = new Privilege();
            toSave.setName(name);
            return this.privilegeRepository.save(toSave);
        } else {
            throw new IllegalArgumentException("The specified name is already used by a privilege");
        }
    }

    /**
     * Update a privilege with the information from the provided PrivilegeDto.
     *
     * @param privilegeDto The PrivilegeDto containing updated privilege information.
     * @return The updated privilege.
     * @throws NoSuchElementException  If the privilege with the given ID does not exist.
     */
    public Privilege update(PrivilegeDto privilegeDto) {
        var toUpdate = this.privilegeRepository.findById(privilegeDto.getId())
            .orElseThrow(() -> new NoSuchElementException("Privilege with the given ID does not exist"));

        toUpdate.setName(privilegeDto.getName());
        return this.privilegeRepository.save(toUpdate);
    }

    /**
     * Delete a privilege with the given ID and update associated roles.
     *
     * @param id The ID of the privilege to delete.
     * @return The deleted privilege.
     * @throws NoSuchElementException If the privilege with the given ID does not exist.
     */
    public Privilege delete(Long id) {
        var toDelete = this.privilegeRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Privilege with the given ID does not exist"));
        var roles = this.roleRepository.findAllByPrivileges(toDelete);
        for (Role role : roles) {
            var tempPrivileges = role.getPrivileges();
            tempPrivileges.remove(toDelete);
            role.setPrivileges(tempPrivileges);
            this.roleRepository.save(role);
        }
        toDelete.setRoles(new ArrayList<>());
        this.privilegeRepository.save(toDelete);
        this.privilegeRepository.delete(toDelete);
        return toDelete;
    }
}
