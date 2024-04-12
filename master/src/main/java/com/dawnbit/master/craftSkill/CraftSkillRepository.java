package com.dawnbit.master.craftSkill;

import com.dawnbit.entity.master.CraftSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CraftSkillRepository extends JpaRepository<CraftSkill, Long> {
    @Query(value = "select * from craft_skill where craft_id=?1", nativeQuery = true)
    List<CraftSkill> findDataByCraftId(Long craftId);

    @Query(value = "select craft_skill_id from craft_skill where craft_id=?1 and skill_id=?2", nativeQuery = true)
    Long findCraftSkillId(Long craftId, Long skillId);


    @Query(value = "select * from craft_skill where craft_id=?1 and skill_id=?2", nativeQuery = true)
    CraftSkill getCraftSkillData(Long craftId, Long skillId);

    @Query(value = "select * from craft_skill where skill_id=?1", nativeQuery = true)
    List<CraftSkill> findDataInCraftBySkillId(Long id);

    @Query(value = "select * from craft_skill where craft_id=?1", nativeQuery = true)
    List<CraftSkill> DeleteCraft(Long id);
}
