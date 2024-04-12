package com.dawnbit.entity.master;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_Id", nullable = false)
    private Long id;

    private String regionName;
}
