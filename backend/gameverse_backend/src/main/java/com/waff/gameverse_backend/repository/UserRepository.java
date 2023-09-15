package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.model.Role;
import com.waff.gameverse_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findAllByRole(Role role);
}
