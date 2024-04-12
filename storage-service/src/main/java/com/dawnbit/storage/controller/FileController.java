package com.dawnbit.storage.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dawnbit.storage.payload.UploadFileResponse;
import com.dawnbit.storage.service.FileStorageService;
import com.dawnbit.storage.util.RestStorageServiceTemplateUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author DB-007
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class FileController {

    /**
     * RESPONSE_TIME
     */
    private static final String RESPONSE_TIME = " ) and response time: ( ";

    /**
     * Autowired FileStorageService
     */
    @Autowired
    private FileStorageService fileStorageService;

    /**
     * @param file
     * @param response
     * @return
     */
    @PostMapping("/uploadFile")
    public Map<String, Object> uploadFile(@RequestParam("file") final MultipartFile file,
                                          final HttpServletResponse response) {
        final UploadFileResponse uploadFileResponse = this.fileStorageService.storeFile(file);
        final Map<String, Object> map = new HashMap<>();
        final Date startDate = new Date();
        map.put("uploadFile", uploadFileResponse);
        if (log.isInfoEnabled()) {
            log.info("Rest url /uploadFile hitting time: ( " + startDate + RESPONSE_TIME + new Date() + ")");
        }
        return RestStorageServiceTemplateUtils.createdSuccessResponse(map, response);
    }

    /**
     * @param files
     * @param response
     * @return
     */
    @PostMapping("/uploadMultipleFiles")
    public Map<String, Object> uploadMultipleFiles(@RequestParam("files") final MultipartFile[] files,
                                                   final HttpServletResponse response) {
        final List<UploadFileResponse> uploadMultipleFiles = this.fileStorageService.storeFiles(files);
        final Map<String, Object> map = new HashMap<>();
        final Date startDate = new Date();
        map.put("uploadMultipleFiles", uploadMultipleFiles);
        if (log.isInfoEnabled()) {
            log.info("Rest url /uploadMultipleFiles hitting time: ( " + startDate + RESPONSE_TIME + new Date() + ")");
        }
        return RestStorageServiceTemplateUtils.createdSuccessResponse(map, response);
    }

    /**
     * @param fileUrl
     * @param response
     * @return
     */
    @PostMapping("/uploadFileFromUrl")
    public Map<String, Object> uploadFileFromUrl(@RequestParam("fileUrl") final String fileUrl,
                                                 final HttpServletResponse response) {
        final UploadFileResponse uploadFileResponse = this.fileStorageService.storeFileFromUrl(fileUrl);
        final Map<String, Object> map = new HashMap<>();
        final Date startDate = new Date();
        map.put("uploadFile", uploadFileResponse);
        if (log.isInfoEnabled()) {
            log.info("Rest url /uploadFileFromUrl hitting time: ( " + startDate + RESPONSE_TIME + new Date() + ")");
        }
        return RestStorageServiceTemplateUtils.createdSuccessResponse(map, response);
    }

    /**
     * @param fileUrls
     * @param response
     * @return
     */
    @PostMapping("/uploadMultipleFilesFromUrls")
    public Map<String, Object> uploadMultipleFilesFromUrls(@RequestParam("fileUrls") final String[] fileUrls,
                                                           final HttpServletResponse response) {

        final List<UploadFileResponse> uploadMultipleFiles = this.fileStorageService.storeFilesFromUrls(fileUrls);
        final Map<String, Object> map = new HashMap<>();
        final Date startDate = new Date();
        map.put("uploadMultipleFiles", uploadMultipleFiles);
        if (log.isInfoEnabled()) {
            log.info("Rest url /uploadMultipleFilesFromUrls hitting time: ( " + startDate + RESPONSE_TIME + new Date()
                    + ")");
        }
        return RestStorageServiceTemplateUtils.createdSuccessResponse(map, response);
    }

    /**
     * @param fileName
     * @param request
     * @return
     */
    @GetMapping("/downloadFile/**")
    public ResponseEntity<Resource> downloadFile(final HttpServletRequest request) {
        log.info("request.getRequestURI() in download:" + request.getRequestURI());
        final String fileName = request.getRequestURI().split(request.getContextPath() + "/downloadFile/")[1];
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

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    /**
     * @param request
     * @return
     */
    @GetMapping("/getFileList")
    public ResponseEntity<List<UploadFileResponse>> getFileList(final HttpServletRequest request) {
        return ResponseEntity.ok(this.fileStorageService.getFileList());
    }

    /**
     * @param fileUrls
     * @param response
     * @return
     * @author DB-0059
     */
    @DeleteMapping("/deleteFileList")
    public Map<String, Object> deleteFileList(@RequestParam("fileUrls") final List<String> fileUrls,
                                              final HttpServletResponse response) {
        final List<Boolean> deleteResponse = this.fileStorageService.deleteFiles(fileUrls);
        final Map<String, Object> map = new HashMap<>();
        final Date startDate = new Date();
        map.put("deleteFiles", deleteResponse);
        if (log.isInfoEnabled()) {
            log.info("Rest url /deleteFileList hitting time: ( " + startDate + RESPONSE_TIME + new Date() + ")");
        }
        return RestStorageServiceTemplateUtils.createdSuccessResponse(map, response);
    }

    @PostMapping("/moveFile")
    public Map<String, Object> moveFile(@RequestParam("fileUrl") final String fileUrl,
                                        @RequestParam("admissionNumber") final String admissionNumber,
                                        @RequestParam("orgCode") final String orgCode, final HttpServletResponse response) {
        final String responseUrl = this.fileStorageService.moveFile(fileUrl,
                orgCode + "/studentdocs/" + admissionNumber);
        final Map<String, Object> map = new HashMap<>();
        final Date startDate = new Date();
        map.put("moveFile", responseUrl);
        if (log.isInfoEnabled()) {
            log.info("Rest url /moveFile hitting time: ( " + startDate + RESPONSE_TIME + new Date() + ")");
        }
        return RestStorageServiceTemplateUtils.createdSuccessResponse(map, response);
    }

    /**
     * @param file
     * @param location dir/subdir/deepsubdir
     * @param response
     * @return
     */
    @PostMapping("/uploadFileWithLocation")
    public Map<String, Object> uploadFile(@RequestParam("file") final MultipartFile file,
                                          @RequestParam("location") final String location,
                                          @RequestParam(required = false, defaultValue = "") final String fileName,
                                          final HttpServletResponse response) {
        final UploadFileResponse uploadFileResponse = this.fileStorageService.storeFile(file, location, fileName);
        final Map<String, Object> map = new HashMap<>();
        final Date startDate = new Date();
        map.put("uploadFile", uploadFileResponse);
        if (log.isInfoEnabled()) {
            log.info(
                    "Rest url /uploadFileWithLocation hitting time: ( " + startDate + RESPONSE_TIME + new Date() + ")");
        }
        return RestStorageServiceTemplateUtils.createdSuccessResponse(map, response);
    }


    @PostMapping("/moveFileToNewLocation")
    public Map<String, Object> moveFileToNewLocation(@RequestBody final List<String> fileUrls,
                                                     @RequestParam("splitString") final String splitString, final HttpServletResponse response) {
        this.fileStorageService.moveFileToNewLocation(fileUrls, splitString);
        final Map<String, Object> map = new HashMap<>();
        final Date startDate = new Date();
        map.put("moveFile", true);
        if (log.isInfoEnabled()) {
            log.info("Rest url /moveFileToNewLocation hitting time: ( " + startDate + RESPONSE_TIME + new Date() + ")");
        }
        return RestStorageServiceTemplateUtils.createdSuccessResponse(map, response);
    }

}
