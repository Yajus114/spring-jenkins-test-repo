package com.dawnbit.master.craftSkill;

import com.dawnbit.entity.master.CraftSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CraftSkillServiceImpl implements CraftSillService {
    @Autowired
    CraftSkillRepository craftSkillRepository;

    @Override
    public List<CraftSkill> fetchCraftSkillsByCraft(Long craftId) {
        return this.craftSkillRepository.findDataByCraftId(craftId);
    }
}
