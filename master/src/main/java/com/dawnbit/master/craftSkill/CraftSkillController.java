package com.dawnbit.master.craftSkill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/craftskill")
public class CraftSkillController {
    @Autowired
    private CraftSillService skillService;

    @GetMapping("v1/fetchCraftSkillsByCraft")
    public ResponseEntity<Map<String, Object>> fetchSkillsByCraft(@RequestParam Long craftId) {

        final Map<String, Object> map = new HashMap<>();

        map.put("data", this.skillService.fetchCraftSkillsByCraft(craftId));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}
