package com.dawnbit.master.OrganisationMaster;

import com.dawnbit.entity.master.OrganisationMaster;
import com.dawnbit.master.externalDTO.OrgListDTO;
import com.dawnbit.master.externalDTO.OrganisationDropDownDTO;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface OrganisationService {
    OrganisationMaster saveOrUpdateOrganisation(OrgListDTO orgListDTO);

    void deleteOrganisation(Long id);

    List<OrganisationMaster> getAllOrganisations();

    Optional<OrganisationMaster> getOrganisationById(Long id);

//    OrganisationMaster checkKeywordDuplicacy(String keyword);

    OrganisationMaster checkOrganisationNameDuplicacy(String keyword, String organisationId);

    Page<OrganisationMaster> getPaginatedOrganisations(Pageable pageable);

    Page<?> fetchOrganisationData(SearchModelDTO searchModel);

    public List<String> getOrganizationIdsByUserRole();

    List<OrganisationDropDownDTO> fetchOrganizations();

    List<OrganisationMaster> getOrganisationListBasedOnRole();

    List<Map<String, Object>> getOrganisationUserCountForDashboard();

    List<OrganisationMaster> getActiveOrganisationListBasedOnRole();
}
