package com.example.found.repository;

import com.example.found.entities.Floor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FloorRepository extends JpaRepository<Floor, Long> {
    List<Floor> findAllByBuildingId(Integer BuildingId);
}
