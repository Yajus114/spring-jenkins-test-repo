package com.dawnbit.master.craft;

import com.dawnbit.entity.master.Craft;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import com.dawnbit.master.externalDTO.dto.CraftDTO;
import com.dawnbit.master.externalDTO.dto.CraftSkillDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CraftService {
    List<Craft> fetchAllCraft();

    String saveAndUpdateCraft(CraftDTO craftDTO);

    Craft fetchCraftById(Long id);

    String deleteCraft(Long craftId);

    String checkDuplicate(String craftName, Long organisationId);

    Page<?> fetchAllCraftBySearchModel(SearchModelDTO searchModelDTO);

    List<Craft> fetchAllCraftByOrganisation(String organisationId);

    List<Craft> fetchAllCraftByOrganisationOnRole();

    List<Craft> fetchCraftsByOrganisation(Long organisationId);

    List<CraftSkillDTO> fetchCraftSkillData(Long craftSkillId);

    String deleteCraftSkill(Long craftSkillId);

    Long isSkillDuplicate(Long craftId, Long skillId);
}
