package com.dawnbit.master.skill;

import com.dawnbit.entity.master.PremiumPay;
import com.dawnbit.entity.master.Skills;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SkillService {
    List<Skills> fetchAllSkill();

    List<PremiumPay> fetchPremiumPayList();

    String saveAndUpdateSkill(Skills skills);

    Skills fetchSkillById(Long skillId);

    Page<?> fetchSkillsBySearchModel(SearchModelDTO searchModel);

    String chechDuplicacyInSkill(String skillName, Long organisationId);

    String deleteSkill(Long skillId);

    /**
     * @param craftId
     * @return
     * @author DB-CPU009
     */
    List<Skills> fetchSkillsByCraft(Long craftId);

//    boolean isSkillNameUnique(String skillName, Long craftId, long organisationId, Integer skillLevel, String types);

    public Long isSkillUnique(String skillName, Integer skillLevel, String types);

}
