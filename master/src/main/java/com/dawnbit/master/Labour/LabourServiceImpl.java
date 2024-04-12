package com.dawnbit.master.Labour;


import com.dawnbit.common.utils.ConstantUtils;
import com.dawnbit.common.utils.PagingSortingUtils;
import com.dawnbit.entity.master.*;
import com.dawnbit.master.OrganisationMaster.OrganisationRepository;
import com.dawnbit.master.OrganisationMaster.OrganisationService;
import com.dawnbit.master.craft.CraftRepository;
import com.dawnbit.master.craftSkill.CraftSkillRepository;
import com.dawnbit.master.externalDTO.LabourDTO;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import com.dawnbit.master.externalDTO.dto.PersonAdditionalDataDTO;
import com.dawnbit.master.labourCraftSkill.LabourCraftSkillRepository;
import com.dawnbit.master.person.PersonRepository;
import com.dawnbit.master.skill.SkillRepository;
import com.dawnbit.master.skill.SkillService;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author DB-CPU009
 */
@Service
@Slf4j
public class LabourServiceImpl implements LabourService {
    private final LabourRepository labourRepository;
    private final CraftRepository craftRepository;
    private final SkillRepository skillRepository;
    private final EntityManager entityManager;
    private final PersonRepository personRepository;
    private final OrganisationRepository organisationRepository;
    private final OrganisationService organisationService;
    private final SkillService skillService;
    @Autowired
    CraftSkillRepository craftSkillRepository;
    @Autowired
    LabourCraftSkillRepository labourCraftSkillRepository;

    // Constructor injection for SkillService

    @Autowired
    LabourServiceImpl(LabourRepository labourRepository, CraftRepository craftRepository, SkillRepository skillRepository, EntityManager entityManager, PersonRepository personRepository, OrganisationRepository organisationRepository, OrganisationService organisationService, SkillService skillService) {
        this.labourRepository = labourRepository;
        this.craftRepository = craftRepository;
        this.skillRepository = skillRepository;
        this.entityManager = entityManager;
        this.personRepository = personRepository;
        this.organisationRepository = organisationRepository;
        this.organisationService = organisationService;
        this.skillService = skillService;
    }


    @Override
    public LabourDTO addUpdateLabour(LabourDTO labourDTO) {

        System.out.println("------------------" + labourDTO.getAdditionalData() + "------------------------");
        Labour labour;
        long labourid = labourDTO.getLabourId();
        Optional<Labour> optionalLabour = labourRepository.findById(labourid);
        //if part is updating the labour
        if (optionalLabour.isPresent()) {
            labour = optionalLabour.get();
            if (labourDTO.isStatus()) {
                labour.setStatus(ConstantUtils.ACTIVE);
            } else {
                labour.setStatus(ConstantUtils.INACTIVE);
            }
            // Fetching the Person entity associated with the Labour
            Person person = labour.getPerson();

            // Call the method to handle additional craft details
            if (labourDTO.getAdditionalData().size() > 0) {
                System.out.println("record is : " + labourDTO.getAdditionalData());
                this.dataInLabourCraftSkill(labourDTO, labour);
            } else {
                this.dataInLabourCraftSkill(labourDTO, labour);
            }

            // Save the updated Labour entity
            labour = labourRepository.save(labour);

            // Update the LabourDTO with the new LabourId
            labourDTO.setLabourId(labour.getId());

            return labourDTO;
        } else {
            // Handle the case where the Labour entity does not exist
            throw new RuntimeException("Labour with ID " + labourid + " not found.");
        }
    }

    public void dataInLabourCraftSkill(LabourDTO labourDTO, Labour labour) {
        if (labourDTO.getLabourId() != null) {
            List<PersonAdditionalDataDTO> additionalData = labourDTO.getAdditionalData();
            for (int i = 0; i < additionalData.size(); i++) {
                if (additionalData.get(i).getCraftId() != 0 && additionalData.get(i).getSkillId() != 0) {
                    LabourCraftSkill lcs = new LabourCraftSkill();
                    Craft c = this.craftRepository.findById(additionalData.get(i).getCraftId()).get();
                    Skills sk = this.skillRepository.findById(additionalData.get(i).getSkillId()).get();
                    Long craftskillId = craftSkillRepository.findCraftSkillId(additionalData.get(i).getCraftId(), additionalData.get(i).getSkillId());
                    CraftSkill csk = this.craftSkillRepository.findById(craftskillId).get();
                    lcs.setCraftSkill(csk);
                    lcs.setLabour(labour);
                    lcs.setOrganisationMaster(labour.getOrganisationMaster());
                    this.labourCraftSkillRepository.save(lcs);
                }
            }
        }
    }

    @Override
    public Page<?> fetchLabourData(SearchModelDTO searchModel) {
        //		final SearchModelDTO searchModel =null;
        Pageable pageable;
        final int limit = searchModel.getLimit();
        final int offset = searchModel.getOffset();
        final String sortField = searchModel.getSortingField();
        final String sortDirection = searchModel.getSortDirection();
        List<String> organizationIds;
        organizationIds = organisationService.getOrganizationIdsByUserRole();
        try (Session session = this.entityManager.unwrap(Session.class)) {
            if (sortField == null) {
                searchModel.setSortingField("prs.id ");
            }
            if (sortDirection == null || sortDirection.isEmpty()) {
                searchModel.setSortDirection("desc");
            }
            final Map<String, Object> params = new HashMap<>();

            String hqlQuery = "select new com.dawnbit.master.externalDTO.SearchModelDTO(lbr,prs,om) FROM Labour lbr " +
                    " inner join Person prs on prs.id=lbr.person.id " +
//                    "inner join Skills skl on skl.skillId=lbr.skills.skillId " +
//                    "inner join  Craft crt on crt.craftId=lbr.craft.craftId " +
                    " inner join  OrganisationMaster om on om.id=lbr.organisationMaster.id "
                    + " where prs.firstName is not null and om.id in(:organizationIds) ";
            /**
             * add like query
             */
            params.put("organizationIds", organizationIds);
            final Map<String, Object> dataMap = PagingSortingUtils.fetchDataListWithUpdatedQueryStringInMaps(params,
                    hqlQuery, searchModel, session);

            final Map<String, Object> resultmap = PagingSortingUtils.addPaginationDataInDataMap(dataMap, params, hqlQuery,
                    false, searchModel, session, "prs");
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
    public LabourDTO getLabourDataById(Long labourId) {
        return this.labourRepository.getLabourDataById(labourId);
    }

    @Override
    public Skills getCraftAndSkillDetails(Long labourId) {
        try {
            // Call the CraftAndSkillDetailsService to fetch craft and skill details based on labourId
//                return skillService.getCraftAndSkillDetailsByLabourId(labourId);
            return null;
        } catch (Exception e) {

            // Log the exception or handle it accordingly
            throw new RuntimeException("Failed to fetch craft and skill details for labourId: " + labourId, e);
        }
    }

    @Override
    public Long isCraftSkillDuplicate(Long labourId, Long craftId, Long skillId) {
        Long craftskillId = craftSkillRepository.findCraftSkillId(craftId, skillId);
        Long res = labourCraftSkillRepository.existsByLabourIdAndCraftIdAndSkillId(labourId, craftskillId);
        return res;
    }
}

