package com.dawnbit.master.skill;

import com.dawnbit.entity.master.Craft;
import com.dawnbit.entity.master.OrganisationMaster;
import com.dawnbit.entity.master.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skills, Long> {

    @Query(value = "select * from skills where craft_id=?1", nativeQuery = true)
    List<Skills> findDataByCraftId(Long craftId);


    @Query(value = "select * from skills where skill_name=?1 and organisation_id=?2", nativeQuery = true)
    Skills chechDuplicacyInSkill(String SkillName, Long orgId);

    @Query(value = "SELECT skill_id FROM skills " +
            "WHERE skill_name = :skillName " +
//            "AND craft_id = :craftId " +
//            "AND organisation_id = :organisationId " +
            "AND skill_level = :skillLevel " +
            "AND types = :types", nativeQuery = true)
    Long isDuplicateSkill(String skillName, Integer skillLevel, String types);

    Skills findBySkillNameAndCraftAndOrganisationMasterAndSkillLevelAndTypes(String skillName, Craft craft, OrganisationMaster org, Integer skillLevel, String types);


}
