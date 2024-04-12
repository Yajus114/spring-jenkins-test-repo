package com.dawnbit.master.OrganisationMaster;

import com.dawnbit.entity.master.OrganisationMaster;
import com.dawnbit.entity.master.Person;
import com.dawnbit.master.externalDTO.OrganisationDropDownDTO;
import com.dawnbit.master.externalDTO.PersonDTO;
import com.dawnbit.master.externalDTO.PersonDropDownDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganisationRepository extends JpaRepository<OrganisationMaster, Long> {

    int countByOrganisationNameIgnoreCase(String organisationName);

    boolean existsByOrganisationNameIgnoreCaseAndIdNot(String organisationName, Long id);

    Page<OrganisationMaster> findAll(Pageable pageable);


//    @Query(value = " select om from OrganisationMaster om where LOWER(om.keyWord) =LOWER(?1) ")
//    OrganisationMaster checkKeywordDuplicacy(String keyword);

    @Query(value = "SELECT om FROM OrganisationMaster om WHERE LOWER(om.organisationName) = LOWER(?1) AND om.id != ?2")
    OrganisationMaster checkOrganisationNameDuplicacy(String keyword, long organisationId);
    OrganisationMaster findByOrganisationNameIgnoreCase(String organization);
    @Query(value = "SELECT om.id FROM organisation_master om where om.organisation_Id != 'GLOBAL' ",nativeQuery = true)
    List<String> getAllOrganizationIds();

    @Query(value = "SELECT * FROM organisation_master om where om.id in (?1) and om.id != 1 ",nativeQuery = true)
    List<OrganisationMaster> getOrganisationListBasedOnRole(List<String> orgIds);

    @Query("select new com.dawnbit.master.externalDTO.OrganisationDropDownDTO(om) "
            + " from OrganisationMaster om WHERE om.id IN ?1 order by om.organisationName ")
    List<OrganisationDropDownDTO> getOrganizationByIds(List<String> finalOrgIds);
    @Query(value = "select count(*) as org_count from organisation_master om where om.organisation_Id != 'Global' and om.id in (?1) and om.status = 'ACTIVE' ",nativeQuery = true)
    String getOrgCount(long id);
    @Query(value = "select count(*) as org_count from organisation_master om where om.organisation_Id != 'Global' and om.status = 'ACTIVE'  ",nativeQuery = true)

    int getOrgCountForAdmin();
    @Query(value = "select count(*) as org_count from organisation_master om where om.organisation_Id != 'Global' and om.status = 'INACTIVE'  ",nativeQuery = true)

    int getInactiveOrgCountForAdmin();

    @Query(value = "SELECT * FROM organisation_master om where om.id in (?1) and om.id != 1 and om.status='ACTIVE' ",nativeQuery = true)
    List<OrganisationMaster> getActiveOrganisationListBasedOnRole(List<String> orgIds);

    @Query(value = "select count(*) as org_count from organisation_master om where om.organisation_Id != 'Global' and om.id in (?1) and om.status = 'INACTIVE' ",nativeQuery = true)
    String getInactiveOrgCount (long id);
}
