package com.dawnbit.master.Region;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @GetMapping("/fetchRegion")
    public ResponseEntity<Map<String, Object>> getAllRegions() {
        final Map<String, Object> map = new HashMap<>();

        map.put("region", this.regionService.getAllRegions());
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

}
