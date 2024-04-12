package com.dawnbit.storage.attachments;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dawnbit.entity.attachments.Attachments;

/**
 * @author DawnBIT
 */
@Service
public interface AttachmentService {

    /**
     * @param file
     * @param fileName
     * @param entity
     * @param location
     * @param orgCode
     * @return
     */
    Attachments saveFile(MultipartFile file, String fileName, String entity, String location, String orgCode);

    /**
     * @param files
     * @param fileNames
     * @param entity
     * @param location
     * @param orgCode
     * @return
     */
    List<Attachments> saveFiles(MultipartFile[] files, String[] fileNames, String entity, String location,
                                String orgCode);

    /**
     * @param fileUrl
     * @param fileName
     * @param entity
     * @param location
     * @param orgCode
     * @return
     */
    Attachments saveFileFromUrl(String fileUrl, String fileName, String entity, String location, String orgCode);

    /**
     * @param fileUrls
     * @param fileNames
     * @param entity
     * @param location
     * @param orgCode
     * @return
     */
    List<Attachments> saveFilesFromUrls(String[] fileUrls, String[] fileNames, String entity, String location,
                                        String orgCode);

    /**
     * @param attachments attachments
     * @return List
     * @author DB-0084
     */
    List<Attachments> findAllAttachmentsByIdList(String attachments);

    /**
     * @param id
     * @return
     */
    Attachments getAttachmentById(long id);

    /**
     * @param attachmentIds id's of attachments to be deleted.
     * @return list of boolean that describes wheither the record is deleted or not?
     * @author DB-0082
     */
    List<Boolean> deleteFiles(List<Long> attachmentIds);

}
