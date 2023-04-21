package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.datamodel.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {
}
