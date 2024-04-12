package com.dawnbit.master.externalDTO.dto;

import com.dawnbit.entity.master.Craft;
import com.dawnbit.entity.master.CraftSkill;
import com.dawnbit.entity.master.LabourCraftSkill;
import com.dawnbit.entity.master.Skills;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonAdditionalDataDTO {

    private Long id;

    private Long craftId;

    private Long skillId;

    private String craftName;

    private String skillName;

    private Integer skillLevel;

    private Double standardRate;
    private Long craftSkillId;

    public PersonAdditionalDataDTO(LabourCraftSkill lcs, Craft c, Skills sc) {
        this.id = lcs.getId();
        this.craftId = c.getCraftId();
        this.craftName = c.getCraftName();
        this.skillId = sc.getSkillId();
        this.skillName = sc.getSkillName();
        this.skillLevel = sc.getSkillLevel();
        this.standardRate = sc.getStandardRate();
    }

    public PersonAdditionalDataDTO(LabourCraftSkill lcs, CraftSkill csk) {
        this.id = lcs.getId();
        this.craftSkillId = csk.getCraftSkillId();
        this.craftId = csk.getCraft().getCraftId();
        this.craftName = csk.getCraft().getCraftName();
        this.skillId = csk.getSkill().getSkillId();
        this.skillName = csk.getSkill().getSkillName();
        this.skillLevel = csk.getSkill().getSkillLevel();
        this.standardRate = csk.getSkill().getStandardRate();
    }
}
