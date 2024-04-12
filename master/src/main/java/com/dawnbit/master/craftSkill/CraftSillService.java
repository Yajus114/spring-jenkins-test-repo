package com.dawnbit.master.craftSkill;

import com.dawnbit.entity.master.CraftSkill;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CraftSillService {
    public List<CraftSkill> fetchCraftSkillsByCraft(Long craftId);
}
