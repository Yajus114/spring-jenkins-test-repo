package com.dawnbit.master.craft;

import com.dawnbit.entity.master.Craft;
import com.dawnbit.master.externalDTO.dto.CraftSkillDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CraftRepository extends JpaRepository<Craft, Long> {

    @Query(value = "SELECT * FROM craft where craft_name=?1 and organisation_id=?2", nativeQuery = true)
    Craft checkDuplicate(String craftName, Long orgId);

    @Query(value = "SELECT ct FROM Craft ct where ct.organisationMaster.id IN ?1")
    List<Craft> fetchAllCraftByOrganisation(String orgIds);

    @Query(value = "SELECT ct FROM Craft ct where ct.organisationMaster.id IN ?1")
    List<Craft> fetchAllCraftByOrganisations(String orgIds);

    @Query(value = "SELECT * FROM craft where organisation_id IN ?1", nativeQuery = true)
    List<Craft> getOrganizationIdsByUserRole(List<String> orgIds);

    @Query(value = "SELECT * FROM craft where organisation_id = ?1", nativeQuery = true)
    List<Craft> findByOrganisationId(Long organisationId);

    @Query("select new com.dawnbit.master.externalDTO.dto.CraftSkillDTO(s,c,cs) "
            + " from CraftSkill cs "
            + " left join Craft c on c.id = cs.craft.id "
            + "left join Skills s on s.id= cs.skill.id"
            + " where c.craftId=?1 ")
    List<CraftSkillDTO> getSkillByCraftId(Long craftId);

//    @Query("select new com.dawnbit.master.externalDTO.dto.CraftDTO(s,c,cs) "
//            + " from CraftSkill cs "
//            + " left join Craft c on c.id = cs.craft.id "
//            + "left join Skills s on s.id= cs.skill.id"
//            +" where cs.id=?1 ")
//
//    CraftDTO getSkillByCraftId(Long craftId);
}
