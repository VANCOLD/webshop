package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.datamodel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
}
