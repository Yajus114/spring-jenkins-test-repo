package com.dawnbit.master.craft;

import com.dawnbit.common.utils.ConstantUtils;
import com.dawnbit.common.utils.PagingSortingUtils;
import com.dawnbit.entity.master.Craft;
import com.dawnbit.entity.master.CraftSkill;
import com.dawnbit.entity.master.LabourCraftSkill;
import com.dawnbit.entity.master.OrganisationMaster;
import com.dawnbit.master.Labour.LabourRepository;
import com.dawnbit.master.OrganisationMaster.OrganisationRepository;
import com.dawnbit.master.OrganisationMaster.OrganisationService;
import com.dawnbit.master.craftSkill.CraftSkillRepository;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import com.dawnbit.master.externalDTO.dto.CraftDTO;
import com.dawnbit.master.externalDTO.dto.CraftSkillDTO;
import com.dawnbit.master.labourCraftSkill.LabourCraftSkillRepository;
import com.dawnbit.master.skill.SkillRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CraftServiceImpl implements CraftService {

    @Autowired
    private CraftRepository craftRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private LabourCraftSkillRepository labourCraftSkillRepository;

    @Autowired
    private LabourRepository labourRepository;

    @Autowired
    private CraftSkillRepository craftSkillRepository;

    @Autowired
    private OrganisationService organisationService;

    @Override
    public List<Craft> fetchAllCraft() {
        return this.craftRepository.findAll();
    }

    @Override
    public String saveAndUpdateCraft(CraftDTO craftDTO) {
        Craft c;

        if (craftDTO.getCraftId() != null && craftDTO.getCraftId() != 0) {
            c = craftRepository.findById(craftDTO.getCraftId()).get();
            //c.setOrganisationMaster(org);
            BeanUtils.copyProperties(craftDTO, c, "craftId", "craftName", "organisationMaster");
            this.craftRepository.save(c);
            if (!craftDTO.getSelectedSkills().isEmpty()) {
                this.craftSkillData(craftDTO, c);
            }
            return "update";
        } else {
            OrganisationMaster org = this.organisationRepository.findById(craftDTO.getOrganisationMaster().getId()).get();
            c = new Craft();
            c.setOrganisationMaster(org);
            BeanUtils.copyProperties(craftDTO, c, "craftId");
            this.craftRepository.save(c);
            if (craftDTO.getSelectedSkills().size() > 0) {
                this.craftSkillData(craftDTO, c);
            }
            return "create";
        }

//        return null;
    }

    public void craftSkillData(CraftDTO craftDTO, Craft craft) {
        List<CraftSkillDTO> selectedSkills = craftDTO.getSelectedSkills();
        for (int i = 0; i < selectedSkills.size(); i++) {
            if (selectedSkills.get(i).getSkillsId() != 0) {
                CraftSkill cs = new CraftSkill();
                cs.setSkill(this.skillRepository.findById(selectedSkills.get(i).getSkillsId()).get());
                cs.setOrganisationMaster(craft.getOrganisationMaster());
                cs.setCraft(craft);
                this.craftSkillRepository.save(cs);
            }
        }
    }


    @Override
    public Craft fetchCraftById(Long id) {
        return this.craftRepository.findById(id).get();
    }

    @Override
    public String deleteCraft(Long id) {
//        List<Skills> skills = this.skillRepository.findDataByCraftId(id);
//        List<Labour> labour = this.labourRepository.findDataInLabourByCraftId(id);
        List<CraftSkill> craftSkill = this.craftSkillRepository.DeleteCraft(id);
        if (!craftSkill.isEmpty()) {
            return "Used";
        } else {
            this.craftRepository.deleteById(id);
            return "delete successfully";
        }
    }

    @Override
    public String checkDuplicate(String craftName, Long orgId) {

        Craft c = this.craftRepository.checkDuplicate(craftName, orgId);
        if (c != null) {
            return "Exists";
        } else {
            return "Not Exists";
        }

    }

    @Override
    public Page<?> fetchAllCraftBySearchModel(SearchModelDTO searchModel) {
        List<String> orgIds = this.organisationService.getOrganizationIdsByUserRole();

        //		final SearchModelDTO searchModel =null;
        Pageable pageable;
        final int limit = searchModel.getLimit();
        final int offset = searchModel.getOffset();
        final String sortField = searchModel.getSortingField();
        final String sortDirection = searchModel.getSortDirection();
//        List<String> organizationIds = new ArrayList<>();
//        organizationIds.add("");

        try (Session session = this.entityManager.unwrap(Session.class)) {
            if (sortField == null) {
                searchModel.setSortingField("c.craftId");
            }
            if (sortDirection == null || sortDirection.isEmpty()) {
                searchModel.setSortDirection("desc");
            }
            final Map<String, Object> params = new HashMap<>();
//			final Map<String, Object> queryMap = new HashMap<>();

            String hqlQuery = "select c "
                    + "FROM Craft c where c.craftName is not null ";

            hqlQuery += " and c.organisationMaster.id in(:orgIds) ";

            //System.out.println("^^^^^^^^^^^^^^^^orgIds^^^^^^^^^^^^^^"+orgIds);

            params.put("orgIds", orgIds);
            final Map<String, Object> dataMap = PagingSortingUtils.fetchDataListWithUpdatedQueryStringInMaps(params,
                    hqlQuery, searchModel, session);
            final Map<String, Object> resultmap = PagingSortingUtils.addPaginationDataInDataMap(dataMap, params,
                    searchModel, session, "c");


            /**
             * Fetch list
             */
            final List<?> searchedLists = (List<?>) resultmap.get(ConstantUtils.DATA_LIST);
            final long totalRows = (long) resultmap.get(ConstantUtils.TOTAL_ROWS);
            pageable = PageRequest.of(offset, limit);
            return new PageImpl<>(searchedLists, pageable, totalRows);
        }
    }

    @Override
    public List<Craft> fetchAllCraftByOrganisation(String organisationId) {
        return this.craftRepository.fetchAllCraftByOrganisations(organisationId);
    }

    @Override
    public List<Craft> fetchAllCraftByOrganisationOnRole() {
        List<String> orgIds = this.organisationService.getOrganizationIdsByUserRole();

        return this.craftRepository.getOrganizationIdsByUserRole(orgIds);
    }

    @Override
    public List<Craft> fetchCraftsByOrganisation(Long organisationId) {
        return craftRepository.findByOrganisationId(organisationId);
    }

    @Override
    public List<CraftSkillDTO> fetchCraftSkillData(Long craftSkillId) {
        System.out.println("LLLLLLLLLLLLLLLLLLL" + craftSkillId);
        return this.craftRepository.getSkillByCraftId(craftSkillId);

//       CraftSkill cs =  craftSkillRepository.findById(craftDTO.getCraftId()).get();
    }

    @Override
    public String deleteCraftSkill(Long craftSkillId) {
        List<LabourCraftSkill> labourCraftSkills = this.labourCraftSkillRepository.deleteCraftSkill(craftSkillId);
        if (!labourCraftSkills.isEmpty()) {
            return "Used";
        } else {
            this.craftSkillRepository.deleteById(craftSkillId);
            return "delete successfully";
        }
    }


//    @Override
//    public String deleteCraftSkill(Long craftSkillId){
//        this.craftSkillRepository.deleteById(craftSkillId);
//        return "craft skill successfully deleted";
//    }

    @Override
    public Long isSkillDuplicate(Long craftId, Long skillId) {
        Long craftSkill = craftSkillRepository.findCraftSkillId(craftId, skillId);
        return craftSkill;
    }
}
