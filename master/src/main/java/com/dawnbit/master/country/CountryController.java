package com.dawnbit.master.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/country")
@RestController
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping("/fetchCountry")
    public ResponseEntity<Map<String, Object>> fetchCountry() {
        final Map<String, Object> map = new HashMap<>();

        map.put("country", this.countryService.fetchCountry());
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @GetMapping("/fetchCountryCode")
    public ResponseEntity<Map<String, Object>> fetchCountryCode(final String countryId) {
        final Map<String, Object> map = new HashMap<>();

        map.put("countryCode", this.countryService.fetchCountryCode(countryId));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @GetMapping("/fetchCountryOfRegion")
    public ResponseEntity<Map<String, Object>> fetchCountryOfRegion(final String regionId) {
        final Map<String, Object> map = new HashMap<>();

        map.put("country", this.countryService.fetchCountryOfRegion(regionId));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}