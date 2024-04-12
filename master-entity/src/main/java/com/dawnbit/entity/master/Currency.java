package com.dawnbit.entity.master;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;



    @Column(name = "country_name")
    private String countryName;


    private String currency;

}
