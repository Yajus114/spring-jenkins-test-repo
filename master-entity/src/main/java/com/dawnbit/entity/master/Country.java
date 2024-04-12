package com.dawnbit.entity.master;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id", nullable = false)
    private Long id;


    private String countryName;


    private String countryCode;

    @ManyToOne
    @JoinColumn(name = "region_Id")
    private Region region;
}
