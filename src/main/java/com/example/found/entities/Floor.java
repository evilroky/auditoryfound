package com.example.found.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "floors")
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer buildingId;
    private Integer floor;
    private String image;
    @Override
    public String toString() {
        return "Floor{id=" + id +
                ", buildingId=" + buildingId +
                ", floor=" + floor +
                ", image='" + image + '\'' +
                '}';
    }
}
