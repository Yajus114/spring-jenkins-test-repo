package com.dawnbit.master.person;

import com.dawnbit.entity.master.Person;
import com.dawnbit.master.externalDTO.PersonDTO;
import com.dawnbit.master.externalDTO.PersonDropDownDTO;
import com.dawnbit.master.externalDTO.dto.PersonAdditionalDataDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("select new com.dawnbit.master.externalDTO.PersonDTO(prs , e , lb) "
            + " from Person prs "
            + " left join Employee e on prs.id = e.person.id "
            + " left join Labour lb on prs.id = lb.person.id "
            + " where prs.id=?1 ")
    PersonDTO getPeronDataById(Long personId);

    @Query("select new com.dawnbit.master.externalDTO.PersonDropDownDTO(prs) "
            + " from Person prs where prs.id not in (?1) ")
    List<PersonDropDownDTO> getPersonIdAndName(List<Long> personIds);

    @Query("select new com.dawnbit.master.externalDTO.PersonDropDownDTO(prs) "
            + " from Person prs ")
    List<PersonDropDownDTO> getPersonIdAndName();

    @Query("select new com.dawnbit.master.externalDTO.PersonDropDownDTO(prs) "
            + " from Person prs where prs.id = ?1 ")
    PersonDropDownDTO getPersonIdAndCurrentPersonWithName(Long personId);

    @Query(value = "SELECT * FROM person WHERE is_employee = TRUE", nativeQuery = true)
    List<Person> findAllEmployees();

    @Query("SELECT new com.dawnbit.master.externalDTO.dto.PersonAdditionalDataDTO(lcs, csk)" +
            " FROM LabourCraftSkill lcs " +
            " INNER JOIN CraftSkill csk ON csk.craftSkillId = lcs.craftSkill.craftSkillId " +
            " WHERE lcs.labour.id = ?1")
    List<PersonAdditionalDataDTO> fetchLabourCraftSkillData(Long labourId);


    @Query(value = "SELECT first_name FROM person WHERE email = ?1 ", nativeQuery = true)
    String checkEmailDuplicacy(String email);
}
