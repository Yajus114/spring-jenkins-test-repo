package com.dawnbit.master.Labour;

//import com.dawnbit.common.exception.CustomException;

import com.dawnbit.entity.master.Skills;
import com.dawnbit.master.externalDTO.LabourDTO;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DB-CPU009
 */
@RestController
@RequestMapping("/api/labour")
@Slf4j
public class LabourController {

    private static final String TO = " ) to : ( ";

    private final LabourService labourService;

    @Autowired
    LabourController(LabourService labourService) {
        this.labourService = labourService;
    }


    /**
     * this api is creating and also updating the Labour
     *
     * @param labourDTO
     * @return
     * @author DB-CPU009
     */
    @PostMapping("/v1/addUpdateLabour")
    ResponseEntity<Map<String, Object>> addUpdateLabour(@RequestBody LabourDTO labourDTO) {
        System.out.println("***************************" + labourDTO + "****************************");
        final Date startDate = new Date();
        Map<String, Object> map = new HashMap<>();
        map.put("data", this.labourService.addUpdateLabour(labourDTO));
        if (log.isInfoEnabled()) {
            log.info("Rest url /addUpdateLabour from : ( " + startDate + TO + new Date() + ")");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(map);
    }

    /**
     * @param searchModel
     * @return fetch paginated Person data
     * @author DB-CPU009
     */
    @PostMapping("v1/fetchLabourData")
    public ResponseEntity<Map<String, Object>> fetchLabourData(@RequestBody SearchModelDTO searchModel) {
        System.out.println("In all record method of fetchLabourData");
        final Date startDate = new Date();
        final Map<String, Object> map = new HashMap<>();
        System.out.println("In all record method of fetchLabourData before method called");
        map.put("data", this.labourService.fetchLabourData(searchModel));
        System.out.println("In all record method of fetchLabourData after method called");
        System.out.println("Result is : " + "data" + this.labourService.fetchLabourData(searchModel));
        if (log.isInfoEnabled()) {
            log.info("Rest url /fetchLabourData from : ( " + startDate + ") to : ( " + new Date() + ")");
        }
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    /**
     * @param labourId
     * @return LabourDTO
     * @author DB-CPU009
     */
    @GetMapping("/v1/getLabourDataById")
    ResponseEntity<Map<String, Object>> getPersonDataById(@RequestParam Long labourId) {
        System.out.println("---------labourId is : -----------------" + labourId);
        final Date startDate = new Date();

        Map<String, Object> map = new HashMap<>();
        map.put("data", this.labourService.getLabourDataById(labourId));

        if (log.isInfoEnabled()) {
            log.info("Rest url /getLabourDataById from : ( " + startDate + ") to : ( " + new Date() + ")");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(map);
    }

    @GetMapping("/v1/getCraftAndSkillDetails")
    public ResponseEntity<?> getCraftAndSkillDetails(@RequestParam Long labourId) {
        try {
            // Call LabourService method to fetch craft and skill details
            Skills details = labourService.getCraftAndSkillDetails(labourId);
            return ResponseEntity.ok(details);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch craft and skill details.");
        }
    }

    @GetMapping("/v1/checkDuplicate")
    public boolean checkDuplicate(@RequestParam Long labourId, @RequestParam Long craftId, @RequestParam Long skillId) {
        // Logic to check if the craft skill ID is already registered for the given labor
        Long res = labourService.isCraftSkillDuplicate(labourId, craftId, skillId);
        System.out.println("--------------result is : -------------" + res);
        if (res != null) {
            return true;
        } else {
            return false;
        }
    }

}
