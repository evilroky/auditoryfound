package com.example.found.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "devices_list")
public class DevicesList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer deviceId;
    private Integer auditoriesId;
    @Column(name = "availability", columnDefinition = "BIT(1)")
    private Boolean availability;
    private String text;
}
