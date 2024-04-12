package com.dawnbit.storage.attachments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dawnbit.storage.util.RestStorageServiceTemplateUtils;

/**
 * @author DawnBIT
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/attachments")
public class AttachmentController {

    /**
     * Injected attachment Service.
     */

    @Autowired
    private AttachmentService attachmentService;

    /**
     * @param file     multipart File
     * @param fileName Name of the file
     * @param location directory location of the file
     * @param entity   table name
     * @param orgCode  organization code
     * @param response
     * @return uploaded file details
     */
    @PostMapping("uploadFile")
    public Map<String, Object> uploadFile(@RequestParam("file") final MultipartFile file,
                                          @RequestParam(value = "fileName", required = false) final String fileName,
                                          @RequestParam(value = "location", required = false) final String location,
                                          @RequestParam(value = "entity", required = false) final String entity,
                                          @RequestParam(value = "orgCode", required = false) final String orgCode,
                                          final HttpServletResponse response) {
        final Map<String, Object> map = new HashMap<>();
        map.put("uploadFile", this.attachmentService.saveFile(file, fileName, entity, location, orgCode));
        return RestStorageServiceTemplateUtils.createdSuccessResponse(map, response);
    }

    /**
     * @param files        files
     * @param entity       entity
     * @param organization organization
     * @param response     response
     * @return uploaded files list
     * @author DB-0061
     */
    @PostMapping("uploadFiles")
    public Map<String, Object> uploadMultipleFiles(@RequestParam("files") final MultipartFile[] files,
                                                   @RequestParam(value = "fileName", required = false) final String[] fileNames,
                                                   @RequestParam(value = "location", required = false) final String location,
                                                   @RequestParam(value = "entity", required = false) final String entity,
                                                   @RequestParam(value = "orgCode", required = false) final String orgCode,
                                                   final HttpServletResponse response) {
        final Map<String, Object> map = new HashMap<>();
        map.put("uploadMultipleFiles", this.attachmentService.saveFiles(files, fileNames, entity, location, orgCode));
        return RestStorageServiceTemplateUtils.createdSuccessResponse(map, response);
    }

    /**
     * @param fileUrl      fileUrl
     * @param entity       entity
     * @param organization organization
     * @param response     response
     * @return uploadFileFromUrl
     */
    @PostMapping("uploadFileFromUrl")
    public Map<String, Object> uploadFileFromUrl(@RequestParam("fileUrl") final String fileUrl,
                                                 @RequestParam(value = "fileName", required = false) final String fileName,
                                                 @RequestParam(value = "location", required = false) final String location,
                                                 @RequestParam(value = "entity", required = false) final String entity,
                                                 @RequestParam(value = "orgCode", required = false) final String orgCode,
                                                 final HttpServletResponse response) {
        final Map<String, Object> map = new HashMap<>();
        map.put("uploadFile", this.attachmentService.saveFileFromUrl(fileUrl, fileName, entity, location, orgCode));
        return RestStorageServiceTemplateUtils.createdSuccessResponse(map, response);
    }

    /**
     * @param fileUrls     fileUrls
     * @param entity       entity
     * @param organization organization
     * @param response     response
     * @return uploadFilesFromUrls
     */
    @PostMapping("uploadFilesFromUrls")
    public Map<String, Object> uploadMultipleFilesFromUrls(@RequestParam("fileUrls") final String[] fileUrls,
                                                           @RequestParam(value = "fileName", required = false) final String[] fileNames,
                                                           @RequestParam(value = "location", required = false) final String location,
                                                           @RequestParam(value = "entity", required = false) final String entity,
                                                           @RequestParam(value = "orgCode", required = false) final String orgCode,
                                                           final HttpServletResponse response) {
        final Map<String, Object> map = new HashMap<>();
        map.put("uploadMultipleFiles",
                this.attachmentService.saveFilesFromUrls(fileUrls, fileNames, entity, location, orgCode));
        return RestStorageServiceTemplateUtils.createdSuccessResponse(map, response);
    }

    /**
     * @param id       attachmentId
     * @param response
     * @return
     */
    @GetMapping("/get/{attachmentId}")
    public Map<String, Object> getAttachment(@PathVariable final long attachmentId,
                                             final HttpServletResponse response) {
        final Map<String, Object> map = new HashMap<>();
        map.put("attachment", this.attachmentService.getAttachmentById(attachmentId));
        return RestStorageServiceTemplateUtils.createdSuccessResponse(map, response);
    }

    /**
     * @param attachmentIds id's of attachments to be deleted.
     * @param response
     * @return list of boolean that describes wheither the record is deleted or not?
     * @author DB-0082
     */
    @DeleteMapping("/deleteAttachmentsAccordingToIds")
    public Map<String, Object> deleteFileList(@RequestParam("attachmentIds") final List<Long> attachmentIds,
                                              final HttpServletResponse response) {
        final List<Boolean> deleteResponse = this.attachmentService.deleteFiles(attachmentIds);
        final Map<String, Object> map = new HashMap<>();
        map.put("deleteFiles", deleteResponse);
        return RestStorageServiceTemplateUtils.createdSuccessResponse(map, response);
    }


}
