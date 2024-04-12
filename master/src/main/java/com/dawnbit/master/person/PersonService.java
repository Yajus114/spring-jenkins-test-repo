package com.dawnbit.master.person;

import com.dawnbit.master.externalDTO.PersonDTO;
import com.dawnbit.master.externalDTO.PersonDropDownDTO;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import com.dawnbit.master.externalDTO.dto.PersonAdditionalDataDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DB-CPU009
 */
@Service
public interface PersonService {
    /**
     * @param personDTO
     * @return
     * @author DB-CPU009
     */
    PersonDTO addUpdatePerson(PersonDTO personDTO);

    /**
     * @param personId
     * @return
     * @author DB-CPU009
     */
    Boolean deletePersonById(String personId);

    /**
     * @param searchModel
     * @return
     * @author DB-CPU009
     */
    Page<?> fetchPersonData(SearchModelDTO searchModel);

    /**
     * @param personId
     * @return
     * @author DB-CPU009
     */
    PersonDTO getPersonDataById(String personId);

    /**
     * @return List<PersonDropDownDTO>
     * @author DB-CPU009
     */
    List<PersonDropDownDTO> getPersonList(String organisationId);

    List<PersonDropDownDTO> getPersonListAndCurrentPerson(String labourId, String organisationId);

    Object getAllPersons();

    Object getPersonListThatAreEmployees();

    List<PersonAdditionalDataDTO> fetchLabourCraftSkillData(Long labourId);

    String checkEmailDuplicacy(String email);

    String deleteCraftSkills(Long labourCraftSkillId);

}
