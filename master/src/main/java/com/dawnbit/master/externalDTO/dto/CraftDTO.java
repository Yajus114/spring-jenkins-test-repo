package com.dawnbit.master.externalDTO.dto;

import com.dawnbit.entity.master.Craft;
import com.dawnbit.entity.master.CraftSkill;
import com.dawnbit.entity.master.OrganisationMaster;
import com.dawnbit.entity.master.Skills;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CraftDTO {

    private Long craftId;

    private String craftName;

    private String description;
    private OrganisationMaster organisationMaster;

    private List<CraftSkillDTO> selectedSkills;

    CraftDTO(Skills s, Craft craft, CraftSkill cs) {
        this.craftId = craft.getCraftId();
        this.craftName = craft.getCraftName();
        this.description = craft.getDescription();
        if (craft.getOrganisationMaster() != null) {
            this.organisationMaster = craft.getOrganisationMaster();
        }


    }


}
