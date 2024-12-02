package com.example.found.repository;

import com.example.found.entities.Auditory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AuditoryRepository extends JpaRepository<Auditory, Long> {
    Optional<Auditory> findByNumberAndFloorsId(Integer number, Integer floorsId);


}
