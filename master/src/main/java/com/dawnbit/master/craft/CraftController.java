package com.dawnbit.master.craft;

import com.dawnbit.entity.master.Craft;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import com.dawnbit.master.externalDTO.dto.CraftDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/craft")
public class CraftController {

    @Autowired
    private CraftService craftService;

    /**
     * this method is used to fetch All Craft Records
     *
     * @return
     */
    @GetMapping("/fetchAllCraft")
    public List<Craft> fetchAllCraft() {
        return craftService.fetchAllCraft();
    }

    @PostMapping("/fetchAllCraftBySearchModel")
    public ResponseEntity<Map<String, Object>> fetchAllCraftBySearchModel(@RequestBody SearchModelDTO searchModel) {

        final Map<String, Object> map = new HashMap<>();

        map.put("craft", this.craftService.fetchAllCraftBySearchModel(searchModel));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    /**
     * Saves or updates a craft based on the provided CraftDTO.
     *
     * @param craftDTO The CraftDTO containing the craft data to save or update.
     * @return A map containing a message indicating the result of the save or update operation.
     * @author Sheetal Saini
     */

    @PostMapping("/saveAndUpdateCraft")
    public Map<String, String> saveAndUpdateCraft(@RequestBody CraftDTO craftDTO) {
        Map<String, String> map = new HashMap<>();
        map.put("data", this.craftService.saveAndUpdateCraft(craftDTO));
        return map;
    }

    /**
     * this is used to craft record by craftId
     *
     * @param craftId
     * @return
     */
    @GetMapping("/fetchCraftById")
    public Craft fetchCraftById(@RequestParam Long craftId) {

        return craftService.fetchCraftById(craftId);
    }


    /**
     * this method is used to delete craft record by craftId
     *
     * @param craftId
     */
    @GetMapping("/deleteCraft")
    public Map<String, String> deleteCraft(@RequestParam Long craftId) {
        Map<String, String> map = new HashMap<>();
        map.put("deleteStatus", this.craftService.deleteCraft(craftId));
        return map;
    }

    /**
     * this method is used to check duplicacy . So duplicate craftName doesnot exists in same organisation
     *
     * @param craftName
     * @param organisationId
     * @return
     */
    @GetMapping("/chechDuplicacy")
    public Map<String, String> chechDuplicacy(@RequestParam String craftName, @RequestParam Long organisationId) {
        Map<String, String> map = new HashMap<>();
        map.put("data", this.craftService.checkDuplicate(craftName, organisationId));
        return map;
    }

    /**
     * fetches all craft by organisation
     *
     * @param organisationId the ID of the organisation
     * @return the list of craft belonging to the organisation
     * @author DB-CPU009 - Ravi kumar
     */
    @GetMapping("/fetchAllCraftByOrganisation")
    public List<Craft> fetchAllCraftByOrganisation(@RequestParam String organisationId) {
        return craftService.fetchAllCraftByOrganisation(organisationId);
    }

    @GetMapping("/craftsBasedOnSelectOrganisation")
    public ResponseEntity<List<Craft>> fetchCraftsByOrganisation(@RequestParam Long organisationId) {
        System.out.println("Organisation id is : " + organisationId);
        List<Craft> crafts = craftService.fetchCraftsByOrganisation(organisationId);
        return ResponseEntity.ok(crafts);
    }

    @GetMapping("/fetchAllCraftByOrganisationOnRole")
    public List<Craft> fetchAllCraftByOrganisationOnRole() {
        return craftService.fetchAllCraftByOrganisationOnRole();
    }


    /**
     * Fetches data related to a specific craft-skill association.
     *
     * @param craftSkillId The ID of the craft-skill association to fetch data for.
     * @return ResponseEntity containing a map with the fetched data as the 'data' attribute.
     * @author Sheetal Saini
     */
    @GetMapping("/fetchCraftSkillData")
    ResponseEntity<Map<String, Object>> fetchCraftSkillData(@RequestParam Long craftSkillId) {
        final Date startDate = new Date();
        Map<String, Object> map = new HashMap<>();
        map.put("data", this.craftService.fetchCraftSkillData(craftSkillId));
        return ResponseEntity.status(HttpStatus.OK)
                .body(map);
    }


    /**
     * @param craftSkillId The ID of the craft-skill association to delete.
     * @return A map containing a message indicating the result of the deletion operation.
     * @author Sheetal Saini
     * Deletes the association between a craft and a skill.
     */
    @GetMapping("/deleteCraftSkill")
    Map<String, String> deleteCraftSkill(@RequestParam Long craftSkillId) {
        Map<String, String> map = new HashMap<>();
        map.put("data", this.craftService.deleteCraftSkill(craftSkillId));
        return map;
    }


    /**
     * @param craftId The ID of the craft to check for duplicate skill association.
     * @param skillId The ID of the skill to check for duplication.
     * @return {@code true} if the skill is already associated with the craft, {@code false} otherwise.
     * @author Sheetal Saini
     * Checks if a given skill is already associated with a craft.
     */
    @GetMapping("checkDuplicateSkill")
    public boolean checkDuplicate(@RequestParam Long craftId, @RequestParam Long skillId) {
        Long craftSkill = craftService.isSkillDuplicate(craftId, skillId);
        if (craftSkill != null) {
            return true;
        } else {
            return false;
        }
    }


}
