package com.dawnbit.entity.master;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CraftSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long craftSkillId;

    @ManyToOne
    @JoinColumn(name="craftId")
    private Craft craft;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skills skill;



    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private OrganisationMaster organisationMaster;

}
