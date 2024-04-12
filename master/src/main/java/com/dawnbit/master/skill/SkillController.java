package com.dawnbit.master.skill;

import com.dawnbit.entity.master.PremiumPay;
import com.dawnbit.entity.master.Skills;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/skill")
public class SkillController {

    @Autowired
    private SkillService skillService;


    @GetMapping("/fetchAllSkill")
    public List<Skills> fetchAllSkill() {
        return skillService.fetchAllSkill();
    }

    @GetMapping("/fetchPremiumPayList")
    public List<PremiumPay> fetchPremiumPayList() {
        return skillService.fetchPremiumPayList();
    }

    /**
     * this method is used to create and update craft
     *
     * @param skills
     * @return
     */
    @PostMapping("/saveAndUpdateSkill")
    public ResponseEntity<Map<String, String>> saveAndUpdateSkill(@RequestBody Skills skills) {
        System.out.println("In skill controller" + skills.getSkillId());

        Map<String, String> response = new HashMap<>();
        try {
            String result = skillService.saveAndUpdateSkill(skills);
            if (result.equals("update") || result.equals("create")) {
                response.put("status", result);
                return ResponseEntity.ok(response);
            } else {
                response.put("error", result);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("error", "An error occurred while saving or updating the skill.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/fetchSkillById")
    public Skills fetchSkillById(@RequestParam Long skillId) {

        return skillService.fetchSkillById(skillId);
    }

    @PostMapping("/fetchSkillsBySearchModel")
    public ResponseEntity<Map<String, Object>> fetchSkillsBySearchModel(@RequestBody SearchModelDTO searchModel) {

        final Map<String, Object> map = new HashMap<>();

        map.put("skill", this.skillService.fetchSkillsBySearchModel(searchModel));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }


    @GetMapping("/chechDuplicacyInSkill")
    public Map<String, String> chechDuplicacyInSkill(@RequestParam String skillName, @RequestParam Long organisationId) {
        Map<String, String> map = new HashMap<>();
//        System.out.println("***********     craft    *******"+craft);
        map.put("data", this.skillService.chechDuplicacyInSkill(skillName, organisationId));
        return map;
    }

    @GetMapping("/deleteSkill")
    public Map<String, String> deleteSkill(@RequestParam Long skillId) {
        Map<String, String> map = new HashMap<>();
        map.put("deleteStatus", this.skillService.deleteSkill(skillId));
        return map;
    }

    /**
     * @param craftId
     * @return List<Skills>
     * @author DB-CPU009
     */
    @GetMapping("v1/fetchSkillsByCraft")
    public ResponseEntity<Map<String, Object>> fetchSkillsByCraft(@RequestParam Long craftId) {

        final Map<String, Object> map = new HashMap<>();

        map.put("data", this.skillService.fetchSkillsByCraft(craftId));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

}
