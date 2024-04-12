package com.dawnbit.master.labourCraftSkill;

import com.dawnbit.entity.master.LabourCraftSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabourCraftSkillRepository extends JpaRepository<LabourCraftSkill, Long> {
    @Query(value = "SELECT id FROM labour_craft_skill " +
            "WHERE labour_id = :labourId " +
            "AND craft_skill_id = :craftSkillId ", nativeQuery = true)
    Long existsByLabourIdAndCraftIdAndSkillId(Long labourId, Long craftSkillId);

    @Query(value = "SELECT * FROM labour_craft_skill lcs WHERE lcs.craft_skill_id = ?1", nativeQuery = true)
    List<LabourCraftSkill> deleteCraftSkill(Long craftSkillId);

    @Query(value = "SELECT * FROM labour_craft_skill lcs WHERE lcs.craft_skill_id = ?1", nativeQuery = true)
    List<LabourCraftSkill> findDataInLabourCraftSkillById(Long id);

    @Query(value = "SELECT labour_id FROM labour_craft_skill lcs WHERE lcs.id = ?1", nativeQuery = true)
    long fetchLabourId(Long labourCraftSkillId);

    @Query(value = "SELECT count(id) FROM labour_craft_skill lcs WHERE lcs.labour_id = ?1", nativeQuery = true)
    int countSkill(long labourId);
}
