package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.datamodel.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long>
{
}
