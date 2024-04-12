package com.dawnbit.master.person;

import com.dawnbit.master.externalDTO.PersonDTO;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import com.dawnbit.master.externalDTO.dto.PersonAdditionalDataDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DB-CPU009
 */
@RestController
@RequestMapping("/api/person")
@Slf4j
public class PersonController {

    // private static final String TO = " ) to : ( ";
    @Autowired
    private PersonService personService;


    /**
     * this api is able to create and also update the person
     *
     * @param personDTO
     * @return
     * @author DB-CPU009
     */
    @PostMapping("/v1/addUpdatePerson")
    ResponseEntity<Map<String, Object>> addUpdatePerson(@RequestBody PersonDTO personDTO) {
        final Date startDate = new Date();
        Map<String, Object> map = new HashMap<>();
        map.put("data", this.personService.addUpdatePerson(personDTO));
        if (log.isInfoEnabled()) {
            log.info("Rest url /addUpdatePerson from : ( " + startDate + ") to : ( " + new Date() + ")");
        }
        return ResponseEntity.ok(map);
    }

    /**
     * @param personId
     * @return PersonDTO
     * @author DB-CPU009
     */
    @GetMapping("/v1/getPersonDataById")
    ResponseEntity<Map<String, Object>> getPersonDataById(@RequestParam String personId) {
        final Date startDate = new Date();

        Map<String, Object> map = new HashMap<>();
        map.put("data", this.personService.getPersonDataById(personId));

        if (log.isInfoEnabled()) {
            log.info("Rest url /getPersonDataById from : ( " + startDate + ") to : ( " + new Date() + ")");
        }
        return ResponseEntity.ok(map);
    }

    /**
     * @return List<PersonDropDownDTO>
     * person list for drop down
     * @author DB-CPU009
     */
    @GetMapping("/v1/getPersonList")
    ResponseEntity<Map<String, Object>> getPersonList(@RequestParam String organisationId) {
        final Date startDate = new Date();

        Map<String, Object> map = new HashMap<>();
        map.put("data", this.personService.getPersonList(organisationId));

        if (log.isInfoEnabled()) {
            log.info("Rest url /getPersonList from : ( " + startDate + ") to : ( " + new Date() + ")");
        }
        return ResponseEntity.ok(map);
    }

    /**
     * @return List<PersonDropDownDTO>
     * person list for drop down
     * @author DB-CPU009
     */
    @GetMapping("/v1/getPersonListAndCurrentPerson")
    ResponseEntity<Map<String, Object>> getPersonListAndCurrentPerson(@RequestParam String labourId, @RequestParam String organisationId) {
        final Date startDate = new Date();

        Map<String, Object> map = new HashMap<>();
        map.put("data", this.personService.getPersonListAndCurrentPerson(labourId, organisationId));

        if (log.isInfoEnabled()) {
            log.info("Rest url /getPersonList from : ( " + startDate + ") to : ( " + new Date() + ")");
        }
        return ResponseEntity.ok(map);
    }

    /**
     * delete person record by person id
     *
     * @param personId
     * @return
     * @author DB-CPU009
     */
    @GetMapping("/v1/deletePersonById")
    ResponseEntity<Map<String, Object>> deletePersonById(@RequestParam String personId) {
        final Date startDate = new Date();

        Map<String, Object> map = new HashMap<>();
        map.put("deleted", this.personService.deletePersonById(personId));
        if (log.isInfoEnabled()) {
            log.info("Rest url /deletePersonById from : ( " + startDate + ") to : ( " + new Date() + ")");
        }
        return ResponseEntity.ok(map);
    }

    /**
     * @param searchModel
     * @return fetch paginated Person data
     */
    @PostMapping("/fetchPersonData")
    public ResponseEntity<Map<String, Object>> fetchPersonData(@RequestBody SearchModelDTO searchModel) {
        final Date startDate = new Date();
        final Map<String, Object> map = new HashMap<>();

        map.put("user", this.personService.fetchPersonData(searchModel));
        if (log.isInfoEnabled()) {
            log.info("Rest url /fetchPersonData from : ( " + startDate + ") to : ( " + new Date() + ")");
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping("/v1/getAllPersons")
    public ResponseEntity<Map<String, Object>> getAllPersons() {
        final Date startDate = new Date();
        final Map<String, Object> map = new HashMap<>();
        map.put("data", personService.getAllPersons());
        if (log.isInfoEnabled()) {
            log.info("Rest url /getAllPersons from : ( " + startDate + ") to : ( " + new Date() + ")");
        }
        return ResponseEntity.ok(map);
    }

    /**
     * @param labourId
     * @return
     * @author Suraj
     * <p>
     * This method used to fetch records from LabourCraftSkill Table
     * This method call from person UI
     */
    @GetMapping("/v1/fetchLabourCraftSkillData")
    ResponseEntity<Map<String, List<PersonAdditionalDataDTO>>> fetchLabourCraftSkillData(@RequestParam Long labourId) {
        final Date startDate = new Date();
        System.out.println("**************************************labour id is : *************" + labourId);
        Map<String, List<PersonAdditionalDataDTO>> map = new HashMap<>();
        map.put("data", this.personService.fetchLabourCraftSkillData(labourId));


        return ResponseEntity.ok(map);
    }


    /**
     * @param email
     * @return
     * @author Suraj
     * <p>
     * This method is used to check that email duplicacy for person
     * This is call from person UI
     */
    @GetMapping("/checkEmailDuplicacy")
    Map<String, String> checkEmailDuplicacy(@RequestParam String email) {
        Map<String, String> map = new HashMap<>();
        map.put("data", this.personService.checkEmailDuplicacy(email));
        return map;
    }

    /**
     * @param labourCraftSkillId
     * @return
     * @author Suraj
     * <p>
     * This method is used to delete records from LabourCraftSkillTable
     * This method call from person UI
     */
    @GetMapping("/deleteCraftSkills")
    Map<String, String> deleteCraftSkills(@RequestParam Long labourCraftSkillId) {
        Map<String, String> map = new HashMap<>();
        map.put("data", this.personService.deleteCraftSkills(labourCraftSkillId));
        return map;
    }
}
