package com.dawnbit.master.externalDTO.dto;

import com.dawnbit.entity.master.Craft;
import com.dawnbit.entity.master.CraftSkill;
import com.dawnbit.entity.master.Skills;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CraftSkillDTO {
    private Long craftSkillId;
    private Long craftId;
    private Long skillsId;
    private String skillName;
    private Double skillRate;
    private String skillDescription;
    private Integer skillLevel;

    CraftSkillDTO(Skills skills) {
        this.skillsId = skills.getSkillId();
        this.skillName = skills.getSkillName();
        this.skillDescription = skills.getDescription();
        this.skillDescription = skills.getDescription();
        this.skillRate = skills.getStandardRate();
        this.skillLevel = skills.getSkillLevel();
    }

    CraftSkillDTO(Skills skills, Craft craft, CraftSkill craftSkill) {
        this.skillsId = skills.getSkillId();
        this.skillName = skills.getSkillName();
        this.skillDescription = skills.getDescription();
        this.skillDescription = skills.getDescription();
        this.skillRate = skills.getStandardRate();
        this.skillLevel = skills.getSkillLevel();
        this.craftId = craft.getCraftId();
        this.craftSkillId = craftSkill.getCraftSkillId();


    }
}
