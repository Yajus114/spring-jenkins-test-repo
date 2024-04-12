package com.dawnbit.master.site;


import com.dawnbit.entity.master.Site;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import com.dawnbit.master.externalDTO.SiteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/sites")
public class SiteController {
    @Autowired
    private SiteService siteService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> saveOrUpdateSite(@RequestBody SiteDTO siteDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("siteList", siteService.saveOrUpdateSite(siteDTO));
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSite(@PathVariable Long id) {
        siteService.deleteSite(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Site>> getAllSites() {
        try {
            List<Site> sites = siteService.getAllSites();
            return ResponseEntity.ok(sites);
        } catch (RuntimeException e) {
//            log.error("Error in fetching all sites", e);
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Site> getSiteById(@PathVariable Long id) {
        Optional<Site> site = siteService.getSiteById(id);
        return site.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/checkSiteNameDuplicacy")
    public ResponseEntity<Map<String, String>> checkSiteNameDuplicacy(@RequestParam String siteName, @RequestParam long orgId) {
        Map<String, String> map = new HashMap<>();
        if (siteService.checkSiteNameDuplicacy(siteName, orgId)) {
            map.put("status", "duplicateSite");
        } else {
            map.put("status", "validSiteName");
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping("/paginated")
    public Page<Site> getPaginatedSites(Pageable pageable) {
        return siteService.getPaginatedSites(pageable);
    }

    //    @PostMapping("/fetchSiteData")
//    public ResponseEntity<Map<String, Object>> fetchSiteData(@RequestBody SearchModelDTO searchModel) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("site", siteService.fetchSiteData(searchModel));
//        return ResponseEntity.ok(map);
//    }
    @PostMapping("/fetchSiteData/{organisationId}")
    public ResponseEntity<Map<String, Object>> fetchSiteData(@RequestBody SearchModelDTO searchModel, @PathVariable String organisationId) {
        Map<String, Object> map = new HashMap<>();
        map.put("site", siteService.fetchSiteData(searchModel, organisationId));
        return ResponseEntity.ok(map);
    }


    @GetMapping("/fetchSiteByOrg/{organisationId}")
    public ResponseEntity<Map<String, Object>> fetchSiteByOrg(@PathVariable String organisationId) {
        final Map<String, Object> map = new HashMap<>();
        map.put("Site", siteService.fetchSiteByOrg(organisationId));
        return ResponseEntity.ok(map);
    }
}
