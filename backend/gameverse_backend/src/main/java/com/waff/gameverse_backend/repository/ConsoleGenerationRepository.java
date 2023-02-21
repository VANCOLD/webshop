package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.datamodel.ConsoleGeneration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsoleGenerationRepository extends JpaRepository<ConsoleGeneration, Long> {
}
