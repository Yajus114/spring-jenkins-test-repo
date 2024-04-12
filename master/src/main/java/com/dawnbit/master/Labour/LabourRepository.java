package com.dawnbit.master.Labour;

import com.dawnbit.entity.master.Labour;
import com.dawnbit.master.externalDTO.LabourDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabourRepository extends JpaRepository<Labour, Long> {

    /**
     * fetch labour details
     *
     * @param labourId
     * @return
     */
//    @Query("SELECT new com.dawnbit.master.externalDTO.LabourDTO(lbr,prs,om) FROM Labour lbr " +
//            " inner join  OrganisationMaster om on om.id=lbr.organisationMaster.id "+
//            " inner join Person prs on prs.id=lbr.person.id  where lbr.id=?1 ")
//    LabourDTO getLabourDataByid(String labourId);
    @Query("SELECT new com.dawnbit.master.externalDTO.LabourDTO(lbr,prs,om) FROM Labour lbr " +
            " inner join  OrganisationMaster om on om.id=lbr.organisationMaster.id " +
            " inner join Person prs on prs.id=lbr.person.id  where lbr.id=?1 ")
    LabourDTO getLabourDataById(Long labourId);

    @Query(value = "select lbr.person.id from Labour lbr inner join Person prs on prs.id=lbr.person.id where lbr.organisationMaster.id = ?1")
    List<Long> getPersonIdsNotInLabour(String organisationId);

    @Query(value = "select lbr.person.id from Labour lbr inner join Person prs on prs.id=lbr.person.id where lbr.id= ?1 ")
    Long getPersonIdAndCurrentperson(String labourId);
}
