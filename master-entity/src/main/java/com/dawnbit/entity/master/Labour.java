package com.dawnbit.entity.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Labour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

//    @ManyToOne
//    @JoinColumn(name = "craft_id")
//    private Craft craft;

//    @ManyToOne
//    @JoinColumn(name = "skills_id")
//    private Skills skills;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private OrganisationMaster organisationMaster;

    private String status;

    private Double standardRate;


}
