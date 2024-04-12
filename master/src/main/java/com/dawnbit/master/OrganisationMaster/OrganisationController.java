package com.dawnbit.master.OrganisationMaster;


import com.dawnbit.common.service.RestServiceTemplateUtils;
import com.dawnbit.entity.master.OrganisationMaster;
import com.dawnbit.master.externalDTO.OrgListDTO;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import com.dawnbit.storage.payload.UploadFileResponse;
import com.dawnbit.storage.service.FileStorageService;
import com.dawnbit.storage.util.RestStorageServiceTemplateUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/organisations")
public class OrganisationController {
    private static final String RESPONSE_TIME = " ) and response time: ( ";
    @Autowired
    private OrganisationService organisationService;
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/create")
    ResponseEntity<Map<String, Object>> createOrUpdateOrganisation(@RequestBody OrgListDTO orgListDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("orgList", this.organisationService.saveOrUpdateOrganisation(orgListDto));
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOrganisation(@PathVariable Long id) {
        organisationService.deleteOrganisation(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrganisationMaster>> getAllOrganisations() {
        try {
            List<OrganisationMaster> organisations = organisationService.getAllOrganisations();
            return ResponseEntity.ok(organisations);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<OrganisationMaster> getOrganisationById(@PathVariable Long id) {
        Optional<OrganisationMaster> organisation = organisationService.getOrganisationById(id);
        return organisation.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * function to check duplicate keyword in the organization
     *
     * @param keyword
     * @return
     */

//    @PostMapping("/checkKeywordDuplicacy")
//    public ResponseEntity<Map<String, Object>> checkKeywordDuplicacy(@RequestParam String keyword) {
//        OrganisationMaster om = this.organisationService.checkKeywordDuplicacy(keyword);
//        final Map<String, Object> map = new HashMap<>();
//        if (om == null) {
//            map.put("keyword", "validKeyword");
//        } else {
//            map.put("keyword", "duplicateKeyword");
//        }
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(map);
//
//    }
    @PostMapping("/checkOrganisationNameDuplicacy")
    public ResponseEntity<Map<String, Object>> checkOrganisationNameDuplicacy(@RequestParam String keyword, @RequestParam String organisationId) {
        OrganisationMaster om = this.organisationService.checkOrganisationNameDuplicacy(keyword, organisationId);
        final Map<String, Object> map = new HashMap<>();
        if (om == null) {
            map.put("organisationName", "validOrganisationName");
        } else {
            map.put("organisationName", "duplicateOrganisationName");
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping("/paginated")
    public Page<OrganisationMaster> getPaginatedOrganisations(Pageable pageable) {
        return organisationService.getPaginatedOrganisations(pageable);
    }

    @PostMapping("/fetchOrganisationData")
    public ResponseEntity<Map<String, Object>> fetchOrganisationData(@RequestBody SearchModelDTO searchModel) {

        final Map<String, Object> map = new HashMap<>();

        map.put("organisation", this.organisationService.fetchOrganisationData(searchModel));
        return ResponseEntity.ok(map);
    }

    @PostMapping("/uploadFileWithLocation")
    public Map<String, Object> uploadFile(@RequestParam("file") final MultipartFile file, @RequestParam("location") final String location, @RequestParam(required = false, defaultValue = "") final String fileName, final HttpServletResponse response) {

        final UploadFileResponse uploadFileResponse = this.fileStorageService.storeFile(file, location, fileName);
        final Map<String, Object> map = new HashMap<>();
        final Date startDate = new Date();
        map.put("uploadFile", uploadFileResponse);
        if (log.isInfoEnabled()) {
            log.info("Rest url /uploadFileWithLocation hitting time: ( " + startDate + RESPONSE_TIME + new Date() + ")");
        }
        return RestStorageServiceTemplateUtils.createdSuccessResponse(map, response);
    }

    /**
     * @return List<OrganisationDropDownDTO>
     * @author DB-CPU009
     */
    @GetMapping("/fetchOrganizationsForDropDown")
    public ResponseEntity<Map<String, Object>> fetchOrganizations() {
        final Map<String, Object> map = new HashMap<>();
        final Date startDate = new Date();
        map.put("data", this.organisationService.fetchOrganizations());
        if (log.isInfoEnabled()) {
            log.info("Rest url /fetchOrganizationsForDropDown hitting time: ( " + startDate + RESPONSE_TIME + new Date() + ")");
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping("/getOrganisationListBasedOnRole")
    public ResponseEntity<List<OrganisationMaster>> getOrganisationListBasedOnRole() {
        List<OrganisationMaster> organisations = organisationService.getOrganisationListBasedOnRole();
        return ResponseEntity.ok(organisations);
    }


    @GetMapping("/getOrganisationUserCountForDashboard")
    public Map<String, Object> getOrganisationUserCountForDashboard(final HttpServletResponse response) {
        final Map<String, Object> map = new HashMap<>();
        map.put("Data", this.organisationService.getOrganisationUserCountForDashboard());
        return RestServiceTemplateUtils.createdSuccessResponse(map, response);
    }


    @GetMapping("/getActiveOrganisationListBasedOnRole")
    public ResponseEntity<List<OrganisationMaster>> getActiveOrganisationListBasedOnRole() {
        List<OrganisationMaster> organisations = organisationService.getActiveOrganisationListBasedOnRole();
        return ResponseEntity.ok(organisations);
    }

    /**
     * @param request
     * @return
     */
    @GetMapping("/downloadFile/**")
    public ResponseEntity<Resource> downloadFile(final HttpServletRequest request) {
        log.info("request.getRequestURI() in download:" + request.getRequestURI());
        final String fileName = request.getRequestURI().split("/downloadFile/")[1];
        // Load file as Resource
        final Resource resource = this.fileStorageService.loadFileAsResource(fileName);
        log.info("fileName in download:" + fileName);
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (final IOException ex) {
            if (log.isInfoEnabled()) {
                log.info("Could not determine file type.");
            }
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"").body(resource);
    }
}
