package com.dawnbit.master.excelUpload;

import com.dawnbit.entity.master.UserSetupTemp;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ExcelUploadService {
    long readAndProcessUserData(MultipartFile multipartFile, Map<String, Object> map);

    Map<String, Object> readUserData(Map<String, Object> map, long processId);

    List<UserSetupTemp> getUserSetupTemp(long processId);

    boolean deleteUserSetupTemp(long processId);

    Map<String, Object> saveUserData(Map<String, Object> map, long processId);

    boolean saveUserSetupTemp(Map<String, Object> map, long processId);

    Page<?> getUserSetupTempRecord(long processId, SearchModelDTO searchingModel);
}
