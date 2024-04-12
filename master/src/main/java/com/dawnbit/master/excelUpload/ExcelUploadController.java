package com.dawnbit.master.excelUpload;

import com.dawnbit.common.exception.CustomException;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/excelUpload")
public class ExcelUploadController {

    @Autowired
    ExcelUploadService excelUploadService;

    @PostMapping("/processUserData")
    public ResponseEntity<Map<String, Object>> readAndProcessUserData(@RequestParam("file") final MultipartFile multipartFile, final RedirectAttributes redirectAttributes, final HttpServletResponse response) {
        final String uploadedFileNameWithExtension = multipartFile.getOriginalFilename();
        final int lastIndexOfDotInUploadedFileName = uploadedFileNameWithExtension.lastIndexOf(".");
        final String extensionOfUploadFile = uploadedFileNameWithExtension
                .substring(lastIndexOfDotInUploadedFileName + 1);
        final Map<String, Object> map = new HashMap<>();
//		final List<OrganizationMaster> orgHierarchyList = this.aspireUserDetailsService
//				.getListOfOrganisationsUnderLoggedInUserOrgHierarchy();
//		map.put("organisationList", orgHierarchyList);
        if (extensionOfUploadFile.equalsIgnoreCase("xls") || extensionOfUploadFile.equalsIgnoreCase("xlsx")) {
            final long processId = this.excelUploadService.readAndProcessUserData(multipartFile,
                    map);
            if (processId > 0) {
                this.excelUploadService.readUserData(map, processId);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(map);

            }
        } else {
            map.put("errorMessage", "PleaseUploadOnlyXlsOrXlsxFile");
//			return "redirect:/protected/excelUpload/processRespondentData";
//            throw new CustomException("INVALID_FILE_TYPE");
        }
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @GetMapping("/cancelUserData")
    public ResponseEntity<Map<String, Boolean>> cancelUserData(@RequestParam("processId") final long processId, final HttpServletResponse response)
            throws CustomException {
        final Map<String, Boolean> map = new HashMap<>();
        map.put("isCancelled", this.excelUploadService.deleteUserSetupTemp(processId));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @GetMapping("/saveUserData")
    public ResponseEntity<Map<String, Object>> saveUserData(@RequestParam("processId") final long processId, final HttpServletResponse response)
            throws CustomException {
        final Map<String, Object> map = new HashMap<>();
        this.excelUploadService.saveUserData(map, processId);

        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @PostMapping("/getUserSetupTempRecord")
    public ResponseEntity<Map<String, Object>> getUserSetupTempRecord(@RequestBody @Valid final SearchModelDTO searchingModel, @RequestParam("processId") final long processId,
                                                                      final HttpServletResponse response) throws CustomException {
        final Map<String, Object> map = new HashMap<>();
        map.put("userSetupTempList", this.excelUploadService.getUserSetupTempRecord(processId, searchingModel));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}
