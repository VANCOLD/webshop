package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.model.Privilege;
import com.waff.gameverse_backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    List<Role> findAllByPrivileges(Privilege privilege);
}
