package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.datamodel.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long>
{
}
