package com.dawnbit.entity.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LabourCraftSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "labour_id")
    private Labour labour;

    @ManyToOne
    @JoinColumn(name = "craft_skill_id")
    private CraftSkill craftSkill;



    @ManyToOne
    @JoinColumn(name = "org_id")
    private OrganisationMaster organisationMaster;
}
