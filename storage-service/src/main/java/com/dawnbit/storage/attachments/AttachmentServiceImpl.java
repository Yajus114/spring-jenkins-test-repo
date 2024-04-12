package com.dawnbit.storage.attachments;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dawnbit.entity.attachments.Attachments;
import com.dawnbit.storage.payload.UploadFileResponse;
import com.dawnbit.storage.service.FileStorageService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author DawnBIT
 */
@Service
@Slf4j
public class AttachmentServiceImpl implements AttachmentService {

    /**
     * Injected FileStorageService
     */
    @Autowired
    private FileStorageService fileStorageService;
    /**
     * Injected AttachmentRepository
     */
    @Autowired
    private AttachmentRepository attachmentRepository;

    /**
     * @param <T>          t
     * @param <U>          u
     * @param listOfString list
     * @param function     function
     * @return list
     * @author DB-0084
     */
    public static <T, U> List<U> convertStringListToIntList(final List<T> listOfString, final Function<T, U> function) {
        return listOfString.stream().map(function).collect(Collectors.toList());
    }

    @Override
    public Attachments saveFile(final MultipartFile file, final String fileName, final String entity,
                                final String location, final String orgCode) {
        log.info(String.format("Upload a single file for org : [%s] and entity : [%s]", orgCode, entity));
        final UploadFileResponse uploadFileResponse = this.fileStorageService.storeFile(file, location, "");
        final Attachments attachments = new Attachments(uploadFileResponse.getFileDownloadUri(),
                fileName == null ? uploadFileResponse.getFileName() : fileName, uploadFileResponse.getFileType(),
                uploadFileResponse.getSize(), entity, location, orgCode);
        return this.attachmentRepository.save(attachments);
    }

    @Override
    public List<Attachments> saveFiles(final MultipartFile[] files, final String[] fileNames, final String entity,
                                       final String location, final String orgCode) {
        log.info(String.format("Upload a multiple files for org : [%s] and entity : [%s]", orgCode, entity));
        final List<UploadFileResponse> uploadFileResponseList = this.fileStorageService.storeFiles(files, location);
        final List<Attachments> attachmentsList = new ArrayList<>();
        int i = 0;
        for (final UploadFileResponse uploadFileResponse : uploadFileResponseList) {
            attachmentsList.add(new Attachments(uploadFileResponse.getFileDownloadUri(),
                    (fileNames == null) || (fileNames.length == 0) ? uploadFileResponse.getFileName()
                            : fileNames.length >= i ? fileNames[i] : fileNames[0],
                    uploadFileResponse.getFileType(), uploadFileResponse.getSize(), entity, location, orgCode));
            i++;
        }
        return (List<Attachments>) this.attachmentRepository.saveAll(attachmentsList);
    }

    @Override
    public Attachments saveFileFromUrl(final String fileUrl, final String fileName, final String entity,
                                       final String location, final String orgCode) {
        log.info(String.format("Upload a single file url for org : [%s] and entity : [%s]", orgCode, entity));
        final UploadFileResponse uploadFileResponse = this.fileStorageService.storeFileFromUrl(fileUrl);
        final Attachments attachments = new Attachments(uploadFileResponse.getFileDownloadUri(),
                fileName == null ? uploadFileResponse.getFileName() : fileName, uploadFileResponse.getFileType(),
                uploadFileResponse.getSize(), entity, location, orgCode);
        return this.attachmentRepository.save(attachments);
    }

    @Override
    public List<Attachments> saveFilesFromUrls(final String[] fileUrls, final String[] fileNames, final String entity,
                                               final String location, final String orgCode) {
        log.info(String.format("Upload a multiple file urls for org : [%s] and entity : [%s]", orgCode, entity));
        final List<UploadFileResponse> uploadFileResponseList = this.fileStorageService.storeFilesFromUrls(fileUrls);
        final List<Attachments> attachmentsList = new ArrayList<>();
        int i = 0;
        for (final UploadFileResponse uploadFileResponse : uploadFileResponseList) {
            attachmentsList.add(new Attachments(uploadFileResponse.getFileDownloadUri(),
                    (fileNames == null) || (fileNames.length == 0) ? uploadFileResponse.getFileName()
                            : fileNames.length >= i ? fileNames[i] : fileNames[0],
                    uploadFileResponse.getFileType(), uploadFileResponse.getSize(), entity, location, orgCode));
            i++;
        }
        return (List<Attachments>) this.attachmentRepository.saveAll(attachmentsList);
    }

    @Override
    public List<Attachments> findAllAttachmentsByIdList(final String attachments) {
        if ((attachments != null) && !attachments.equals("")) {
            final List<String> list = Stream.of(attachments.split(","))
                    .collect(Collectors.toCollection(ArrayList::new));

            final List<Long> attachmentIdList = convertStringListToIntList(list, Long::parseLong);
            return this.attachmentRepository.findAllAttchmentsByIds(attachmentIdList);
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public Attachments getAttachmentById(final long id) {
        return this.attachmentRepository.findById(id).orElseThrow();
    }

    @Transactional
    @Override
    public List<Boolean> deleteFiles(final List<Long> attachmentIds) {
        final List<Boolean> response = new ArrayList<>();
        final List<String> listOfDownloadURI = this.attachmentRepository.getAllDownloadURIBasedOnIds(attachmentIds);
        this.attachmentRepository.deleteByIdIn(attachmentIds);
        for (final String fileUrl : listOfDownloadURI) {
            response.add(this.deleteTheFileForDirectory(fileUrl));
        }
        return response;
    }

    public Boolean deleteTheFileForDirectory(final String fileUrl) {
        if (!StringUtils.isEmpty(fileUrl) && (fileUrl.indexOf("downloadFile/") != -1)) {
            try {
                final Path fileLocation = this.fileStorageService.getFileStorageLocationPath()
                        .resolve(fileUrl.substring(fileUrl.indexOf("downloadFile/") + 13));
                return Files.deleteIfExists(fileLocation);
            } catch (final IOException e) {
                log.error(e.getMessage(), e);
                return false;
            }
        }
        return false;
    }
}
