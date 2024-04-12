package com.dawnbit.entity.master;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Qualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qualificationId;

    private String qualificationName;

    private String description;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private OrganisationMaster organisationMaster;

    @ManyToOne
    @JoinColumn(name = "skills_id")
    private Skills skills;

}
