package com.example.found.repository;

import com.example.found.entities.DevicesList;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DevicesListRepository extends JpaRepository<DevicesList, Integer> {
    List<DevicesList> findByAuditoriesId(Integer auditoriesId);

    @Transactional
    @Modifying
    @Query("UPDATE DevicesList d SET d.deviceId = :deviceId, d.availability = :availability, d.text = :text WHERE d.id = :deviceListId")
    void updateDeviceInfo(@Param("deviceListId") Integer deviceListId,
                          @Param("deviceId") Integer deviceId,
                          @Param("availability") Boolean availability,
                          @Param("text") String text);


}
