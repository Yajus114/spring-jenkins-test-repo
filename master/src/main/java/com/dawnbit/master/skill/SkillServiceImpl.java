package com.dawnbit.master.skill;

import com.dawnbit.common.utils.ConstantUtils;
import com.dawnbit.common.utils.PagingSortingUtils;
import com.dawnbit.entity.master.CraftSkill;
import com.dawnbit.entity.master.PremiumPay;
import com.dawnbit.entity.master.Skills;
import com.dawnbit.master.Labour.LabourRepository;
import com.dawnbit.master.OrganisationMaster.OrganisationRepository;
import com.dawnbit.master.OrganisationMaster.OrganisationService;
import com.dawnbit.master.craft.CraftRepository;
import com.dawnbit.master.craftSkill.CraftSkillRepository;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import com.dawnbit.master.premiumPay.PremiumPayRepository;
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
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private PremiumPayRepository premiumPayRepository;

    @Autowired
    private CraftRepository craftRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private LabourRepository labourRepository;

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private CraftSkillRepository craftSkillRepository;


    @Override
    public List<Skills> fetchAllSkill() {
        return this.skillRepository.findAll();
    }

    @Override
    public List<PremiumPay> fetchPremiumPayList() {
        return this.premiumPayRepository.findAll();
    }


    /**
     * Saves or updates a skill based on the provided Skills object.
     *
     * @param skills The Skills object containing the skill data to save or update.
     * @return A message indicating the result of the save or update operation.
     * @author Sheetal Saini
     */
    @Override
    public String saveAndUpdateSkill(Skills skills) {
        long ids = skills.getSkillId();
        if (ids != 0) {
            Skills existingSkill = skillRepository.getReferenceById(ids);
            existingSkill.setDescription(skills.getDescription()); // Update description
            existingSkill.setStandardRate(skills.getStandardRate()); // Update price
            this.skillRepository.save(existingSkill);
            return "update";
        } else {
            // Check for duplicacy
            Long isUnique = isSkillUnique(skills.getSkillName(), skills.getSkillLevel(), skills.getTypes());
            if (isUnique != null) {
                return "Duplicate skill details found.";
            } else {
                // If the skill doesn't exist, save it as a new skill
                Skills newSkill = new Skills();
                BeanUtils.copyProperties(skills, newSkill, "skillId");
//            newSkill.setCraft(craft);
//            newSkill.setOrganisationMaster(org);
                this.skillRepository.save(newSkill);
                return "create";
            }
        }
    }

    @Override
    public Skills fetchSkillById(Long id) {
        return this.skillRepository.findById(id).get();
    }


    @Override
    public Page<?> fetchSkillsBySearchModel(SearchModelDTO searchModel) {

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
                searchModel.setSortingField("s.skillId");
            }
            if (sortDirection == null || sortDirection.isEmpty()) {
                searchModel.setSortDirection("desc");
            }
            final Map<String, Object> params = new HashMap<>();
//			final Map<String, Object> queryMap = new HashMap<>();

            String hqlQuery = "select s "
                    + "FROM Skills s where s.skillName is not null ";
//                    " left join  OrganisationMaster om on om.id=prs.organisationMaster.id ";

//            hqlQuery+= " and s.organisationMaster.id in(:orgIds) ";

//            params.put("orgIds",orgIds);
            final Map<String, Object> dataMap = PagingSortingUtils.fetchDataListWithUpdatedQueryStringInMaps(params,
                    hqlQuery, searchModel, session);
            final Map<String, Object> resultmap = PagingSortingUtils.addPaginationDataInDataMap(dataMap, params,
                    searchModel, session, "s");


            /**
             * Fetch list
             */
            final List<?> searchedLists = (List<?>) resultmap.get(ConstantUtils.DATA_LIST);
            System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH" + searchedLists.toString());
            final long totalRows = (long) resultmap.get(ConstantUtils.TOTAL_ROWS);
            pageable = PageRequest.of(offset, limit);
            return new PageImpl<>(searchedLists, pageable, totalRows);
        }
    }


    @Override
    public String chechDuplicacyInSkill(String skillName, Long orgId) {

        Skills sk = this.skillRepository.chechDuplicacyInSkill(skillName, orgId);
        if (sk != null) {
            return "Exists";
        } else {
            return "Not Exists";
        }
    }

    @Override
    public String deleteSkill(Long id) {
        List<CraftSkill> craftSkill = this.craftSkillRepository.findDataInCraftBySkillId(id);
        System.out.println("hhhhhhhhHHHHHHHHHHHHHHHHHHHHHHHHhhhhhhhhhhhhhhhhhhh" + craftSkill);
        if (!craftSkill.isEmpty()) {
            return "Used";
        } else {
            this.skillRepository.deleteById(id);
            return "delete successfully";
        }
    }

    @Override
    public List<Skills> fetchSkillsByCraft(Long craftId) {
        return this.skillRepository.findDataByCraftId(craftId);
    }

    //    @Override
    public Long isSkillUnique(String skillName, Integer skillLevel, String types) {
        return skillRepository.isDuplicateSkill(skillName, skillLevel, types);

    }

}
