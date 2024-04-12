/*
 *
 */
package com.dawnbit.storage.service;

import com.dawnbit.storage.exception.FileStorageException;
import com.dawnbit.storage.exception.MyFileNotFoundException;
import com.dawnbit.storage.payload.UploadFileResponse;
import com.dawnbit.storage.property.FileStorageProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author DB-007
 */
@Slf4j
@Service
public class FileStorageService {

    /**
     * SLASH
     */
    private static final String SLASH = "/";
    /**
     * fileStoragePath
     */
    private final Path fileStoragePath;
    /**
     * fileStorageLocation
     */
    private final Path fileStorageLocation;
    /**
     * filePublicDomain
     */
    private final String filePublicDomain;

    /**
     * @param fileStorageProperties
     */
    @Autowired
    public FileStorageService(final FileStorageProperties fileStorageProperties) {
        this.fileStoragePath = Paths.get(fileStorageProperties.getUploadDir());
        this.fileStorageLocation = this.fileStoragePath.toAbsolutePath().normalize();
        this.filePublicDomain = fileStorageProperties.getPublicDomain();
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (final IOException ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
                    ex);
        }
    }

    /**
     * @param fileName
     * @return
     */
    public String generateFileUri(final String fileName) {
        return this.filePublicDomain + "/downloadFile/" + fileName;
    }

    public Path getFileStorageLocationPath() {
        return this.fileStorageLocation;
    }

    /**
     * @param files
     * @return
     */
    public List<UploadFileResponse> storeFilesList(final List<MultipartFile> files) {
        return this.storeFiles(files.toArray(MultipartFile[]::new));
    }

    /**
     * @param files
     * @return
     */
    public List<UploadFileResponse> storeFiles(final MultipartFile... files) {
        final List<UploadFileResponse> uploadMultipleFiles = new ArrayList<>();
        for (final MultipartFile file : files) {
            uploadMultipleFiles.add(this.storeFile(file));
        }
        return uploadMultipleFiles;
    }

    /**
     * @param files
     * @param location
     * @return
     */
    public List<UploadFileResponse> storeFilesList(final List<MultipartFile> files, final String location) {
        return this.storeFiles(files.toArray(MultipartFile[]::new), location);
    }

    /**
     * @param files
     * @param location
     * @return
     */
    public List<UploadFileResponse> storeFiles(final MultipartFile[] files, final String location) {
        final List<UploadFileResponse> uploadMultipleFiles = new ArrayList<>();
        for (final MultipartFile file : files) {
            uploadMultipleFiles.add(this.storeFile(file, location, ""));
        }
        return uploadMultipleFiles;
    }

    /**
     * @param multiPart
     * @return
     */
    private String generateFileNameFromMultipart(final MultipartFile multiPart, final String nameOfFile) {
        if ((nameOfFile != null) && !nameOfFile.isBlank()) {
            return new Date().getTime() + "-" + nameOfFile;
        } else {
            String fileName = multiPart.getOriginalFilename().replace("\\", SLASH).replace(" ", "_");
            final int lastIndex = fileName.lastIndexOf(SLASH);
            if (lastIndex != -1) {
                fileName = fileName.substring(lastIndex + 1);
            }

            return new Date().getTime() + "-" + fileName;
        }
    }

    /**
     * @param file
     * @return
     */
    public UploadFileResponse storeFile(final MultipartFile file) {
        return this.storeFile(file, null, "");
    }

    /**
     * @param file
     * @param location
     * @return
     */
    public UploadFileResponse storeFile(final MultipartFile file, final String location, final String nameOfFile) {
        System.out.println("%%%%%%%%%%%%%%%%%%%%%");
        log.info("FILE : " + file);
        // Normalize file name
        final String fileName = StringUtils.cleanPath(this.generateFileNameFromMultipart(file, nameOfFile));
        log.info("FILE_NAME : " + fileName);
//		location = location != null ? StringUtils.startsWithIgnoreCase(location, SLASH) ? location : SLASH + location
//				: "";
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            log.info("FILE_STORAGE_LOCN : " + this.fileStorageLocation);
            final Path rootDirectory = Paths.get(this.fileStorageLocation + "//" + location).toAbsolutePath()
                    .normalize();
            final Path targetLocation = Files.createDirectories(rootDirectory).resolve(fileName);
            log.info("TARGET_LOCN : " + targetLocation);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return new UploadFileResponse(fileName, this.generateFileUri(location + SLASH + fileName),
                    file.getContentType(), file.getSize());
        } catch (final IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }


    public String uploadFileFromExcel(final InputStream is, String contentType, String fileExtension) {

        // Create a new Date object
        Date currentDate = new Date();

        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        // Format the Date object into a string
        String formattedDate = dateFormat.format(currentDate);


        String location = "Aspire/unmanaged/" + formattedDate;

        String nameOfFile = "OrgImg." + fileExtension;
        log.info("nameOfFile : " + nameOfFile);

        final String fileName = StringUtils.cleanPath(this.generateFileNameForExcel(nameOfFile));

        try {

            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            log.info("FILE_STORAGE_LOCN : " + this.fileStorageLocation);
            final Path rootDirectory = Paths.get(this.fileStorageLocation + "//" + location).toAbsolutePath()
                    .normalize();
            final Path targetLocation = Files.createDirectories(rootDirectory).resolve(fileName);
            log.info("TARGET_LOCN : " + targetLocation);
            Files.copy(is, targetLocation, StandardCopyOption.REPLACE_EXISTING);


            return this.generateFileUri(location + SLASH + fileName);
        } catch (final IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }

    }

    private String generateFileNameForExcel(final String nameOfFile) {
//		if ((nameOfFile != null) && !nameOfFile.isBlank()) {
        return new Date().getTime() + "-" + nameOfFile;
//		} else {
//			String fileName = multiPart.getOriginalFilename().replace("\\", SLASH).replace(" ", "_");
//			final int lastIndex = fileName.lastIndexOf(SLASH);
//			if (lastIndex != -1) {
//				fileName = fileName.substring(lastIndex + 1);
//			}
//
//			return new Date().getTime() + "-" + fileName;
//		}
    }

    public List<UploadFileResponse> storeFilesFromUrls(final String[] fileUrls) {
        final List<UploadFileResponse> uploadMultipleFiles = new ArrayList<>();
        for (final String fileUrl : fileUrls) {
            uploadMultipleFiles.add(this.storeFileFromUrl(fileUrl));
        }
        return uploadMultipleFiles;
    }

    public UploadFileResponse storeFileFromUrl(final String fileUrl) {
        try {
            final URL url = new URL(fileUrl);
            final URLConnection connection = url.openConnection();
            final InputStream in = connection.getInputStream();
            final File file = new File(url.getFile());
            // Normalize file name
            final String fileName = StringUtils.cleanPath(this.generateFileName());
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            final Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(in, targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return new UploadFileResponse(fileName, this.generateFileUri(fileName),
                    Files.probeContentType(new File(fileName).toPath()), (int) file.length());
        } catch (final IOException ex) {
            throw new FileStorageException("Could not store file " + fileUrl + ". Please try again!", ex);
        }
    }

    private String generateFileName() {
        return String.valueOf(new Date().getTime());
    }

    public Resource loadFileAsResource(final String fileName) {
        try {
            final Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            final Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (final MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public List<UploadFileResponse> getFileList() {
        try {
            final List<UploadFileResponse> uploadFileResponseList = new ArrayList<>();
            Files.walk(this.fileStoragePath).filter(Files::isRegularFile).forEach(file -> {
                try {
                    uploadFileResponseList.add(new UploadFileResponse(file.toFile().getName(),
                            this.generateFileUri(file.toFile().getName()), Files.probeContentType(file),
                            file.toFile().length()));
                } catch (final IOException ex) {
                    throw new FileStorageException("Could not find files. Please try again!", ex);
                }
            });
            return uploadFileResponseList;
        } catch (final IOException ex) {
            throw new FileStorageException("Could not find files. Please try again!", ex);
        }
    }

    /**
     * @param fileUrl which you want to delete
     * @return boolean true value for successfully delete
     */
    public Boolean deleteFile(final String fileUrl) {
        if (!StringUtils.isEmpty(fileUrl) && (fileUrl.indexOf("downloadFile/") != -1)) {
            try {
                final Path fileLocation = this.fileStorageLocation
                        .resolve(fileUrl.substring(fileUrl.indexOf("downloadFile/") + 13));
                return Files.deleteIfExists(fileLocation);
            } catch (final IOException e) {
                log.error(e.getMessage(), e);
                return false;
            }
        }
        return false;
    }

    /**
     * @param fileUrls list of file urls which you want to delete
     * @return response list of boolean values true value for successfully deleted
     * files
     */
    public List<Boolean> deleteFiles(final List<String> fileUrls) {
        final List<Boolean> response = new ArrayList<>();
        for (final String fileUrl : fileUrls) {
            response.add(this.deleteFile(fileUrl));
        }
        return response;
    }

    public List<String> moveFiles(final List<String> fileUrls, final String newFilePath) {
        final List<String> response = new ArrayList<>();
        for (final String fileUrl : fileUrls) {
            response.add(this.moveFile(fileUrl, newFilePath));
        }
        return response;
    }

    /**
     * @param fileUrl     http://host:port/downloadFile/location/filename.ext
     * @param newFilePath dir/subdir/deepsubdir
     * @return http://host:port/downloadFile/dir/subdir/deepsubdir/filename.ext
     */
    public String moveFile(final String fileUrl, final String newFilePath) {
        if (!StringUtils.isEmpty(fileUrl) && (fileUrl.indexOf("downloadFile/") != -1)) {
            try {
                final String fileName = fileUrl.substring(fileUrl.indexOf("downloadFile/") + 13);
                String fileName1 = fileName;
                if (fileName.lastIndexOf("/") != -1) {
                    fileName1 = fileName.substring(fileName.lastIndexOf("/")).replace("/", "");
                }
                if (log.isInfoEnabled()) {
                    log.info("fileName : " + fileName1);
                }
                final Path fileLocation = this.fileStorageLocation.resolve(fileName);
                final Path fileNewPath = this.fileStorageLocation.resolve(newFilePath).toAbsolutePath().normalize();
                final Path fileNewLocation = Files.createDirectories(fileNewPath).resolve(fileName1);
                if (log.isInfoEnabled()) {
                    log.info("fileLocation : " + fileLocation);
                    log.info("fileNewLocation : " + fileNewLocation);
                }
                Files.move(fileLocation, fileNewLocation, StandardCopyOption.REPLACE_EXISTING);
                return this.generateFileUri(newFilePath + "/" + fileName1);
            } catch (final Exception e) {
                if (log.isInfoEnabled()) {
                    log.error(e.getMessage(), e);
                }
                return fileUrl;
            }
        }
        return fileUrl;
    }


    /**
     * @param fileUrl     http://host:port/downloadFile/location/filename.ext
     * @param newFilePath dir/subdir/deepsubdir
     * @return http://host:port/downloadFile/dir/subdir/deepsubdir/filename.ext
     */
    public void moveFileToNewLocation(final List<String> fileUrls, String splitString) {
        for (String fileUrl : fileUrls) {

            //final String newFilePath=fileUrl.replace(splitString, splitString.replace('/','_'));
            String s = fileUrl.split("/" + splitString + "/")[1];
            s = s.substring(0, 16).replace("/", "_");

            final String newFilePath = "ASPIRE_DIAGS/applicant_doc/" + s.split("_")[3] + "/" + s;

            log.info("newFilePath----" + newFilePath);

            if (!StringUtils.isEmpty(fileUrl) && (fileUrl.indexOf("downloadFile/") != -1)) {
                try {
                    final String fileName = fileUrl.substring(fileUrl.indexOf("downloadFile/") + 13);
                    String fileName1 = fileName;
                    if (fileName.lastIndexOf("/") != -1) {
                        fileName1 = fileName.substring(fileName.lastIndexOf("/")).replace("/", "");
                    }
                    if (log.isInfoEnabled()) {
                        log.info("fileName : " + fileName1);
                    }
                    final Path fileLocation = this.fileStorageLocation.resolve(fileName);
                    final Path fileNewPath = this.fileStorageLocation.resolve(newFilePath).toAbsolutePath().normalize();
                    final Path fileNewLocation = Files.createDirectories(fileNewPath).resolve(fileName1);
                    if (log.isInfoEnabled()) {
                        log.info("fileLocation : " + fileLocation);
                        log.info("fileNewLocation : " + fileNewLocation);
                    }
                    Files.copy(fileLocation, fileNewLocation, StandardCopyOption.COPY_ATTRIBUTES);
                } catch (final Exception e) {
                    if (log.isInfoEnabled()) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }

    }

    public String copyFile(final String fileUrl, final String newFilePath) {
        if (!StringUtils.isEmpty(fileUrl) && (fileUrl.indexOf("downloadFile/") != -1)) {
            try {
                final String fileName = fileUrl.substring(fileUrl.indexOf("downloadFile/") + 13);
                String fileName1 = fileName;
                if (fileName.lastIndexOf("/") != -1) {
                    fileName1 = fileName.substring(fileName.lastIndexOf("/")).replace("/", "");
                }
                if (log.isInfoEnabled()) {
                    log.info("fileName : " + fileName1);
                }
                final Path fileLocation = this.fileStorageLocation.resolve(fileName);
                final Path fileNewPath = this.fileStorageLocation.resolve(newFilePath).toAbsolutePath().normalize();
                final Path fileNewLocation = Files.createDirectories(fileNewPath).resolve(fileName1);
                if (log.isInfoEnabled()) {
                    log.info("fileLocation : " + fileLocation);
                    log.info("fileNewLocation : " + fileNewLocation);
                }
                Files.copy(fileLocation, fileNewLocation, StandardCopyOption.COPY_ATTRIBUTES);
                return this.generateFileUri(newFilePath + "/" + fileName1);
            } catch (final Exception e) {
                if (log.isInfoEnabled()) {
                    log.error(e.getMessage(), e);
                }
                return fileUrl;
            }
        }
        return fileUrl;
    }

    public UploadFileResponse saveBirtReport(final MultipartFile file) {
        final String fileName = StringUtils.cleanPath(this.generateFileNameFromMultipart(file, ""));

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            final Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return new UploadFileResponse(fileName, this.generateFileUri(fileName), file.getContentType(),
                    file.getSize());
        } catch (final IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    /**
     * @param location
     * @return
     */
    public boolean deleteContentAtLocation(final String location, final Date date) {
        final Path rootDirectory = this.fileStorageLocation.resolve(location).toAbsolutePath().normalize();
        try {
            Files.walk(rootDirectory).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(file -> {
                final Date lastMod = new Date(file.lastModified());
                if (lastMod.before(date)) {
                    file.delete();
                }
            });
            return true;
        } catch (final IOException e) {
            log.info(e.getMessage());
            return false;
        }
    }

    public List<String> getFolderList(String location) {
        location = location != null ? StringUtils.startsWithIgnoreCase(location, SLASH) ? location : SLASH + location
                : "";
        final File file = new File(this.fileStorageLocation + location);
        final File[] files = file.listFiles((current, name) -> new File(current, name).isDirectory());
        if ((files == null) || (files.length == 0)) {
            throw new FileStorageException("NO_FOLDER_EXISTS");
        }
        Arrays.sort(files, (f1, f2) -> {
            // final BasicFileAttributes f1Attr =
            // Files.readAttributes(Paths.get(f1.toURI()),
//						BasicFileAttributes.class);
//				final BasicFileAttributes f2Attr = Files.readAttributes(Paths.get(f2.toURI()),
//						BasicFileAttributes.class);
//				return f2Attr.lastModifiedTime().compareTo(f1Attr.lastModifiedTime());
            return Long.compare(f2.lastModified(), f1.lastModified());
        });
        final List<String> folderList = Arrays.asList(files).stream().map(File::getName).collect(Collectors.toList());
//		if (folderList == null && folderList.isEmpty()) {
//			throw new FileStorageException("NO_FOLDER_EXISTS");
//		}
        return folderList;
    }

    public int createFolder(String location, final String folderName) {
        System.out.println("createFolder------------------->" + location + " : " + folderName);
        location = location != null ? StringUtils.startsWithIgnoreCase(location, SLASH) ? location : SLASH + location
                : "";
        try {
            // Copy file to the target location (Replacing existing file with the same name)
            log.info("FILE_STORAGE_LOCN : " + this.fileStorageLocation);
            final Path rootDirectory = Paths.get(this.fileStorageLocation + location + SLASH + folderName)
                    .toAbsolutePath().normalize();
            if (Files.exists(rootDirectory)) {
                throw new FileStorageException("FOLDER_ALREADY_EXISTS");
            }
            final Path targetLocation = Files.createDirectories(rootDirectory);
            System.out.println("targetLocation :-------------------- " + targetLocation);
            return 1;
        } catch (final IOException ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
                    ex);
        }
    }

    public int deleteFolder(String location, final String folderName) {
        System.out.println("createFolder------------------->" + location + " : " + folderName);
        location = location != null ? StringUtils.startsWithIgnoreCase(location, SLASH) ? location : SLASH + location
                : "";
        try {
            // Copy file to the target location (Replacing existing file with the same name)
            log.info("FILE_STORAGE_LOCN : " + this.fileStorageLocation);
            final Path rootDirectory = Paths.get(this.fileStorageLocation + location + SLASH + folderName)
                    .toAbsolutePath().normalize();
            Files.walk(rootDirectory).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
            log.info("Directory still exists : " + Files.exists(rootDirectory));
            return 1;
        } catch (final IOException ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
                    ex);
        }
    }

    /**
     * @param files
     * @param location
     * @return
     */
    public List<UploadFileResponse> storeGallaryImageList(final List<MultipartFile> files, final String location) {
        return this.storeGallaryImages(files.toArray(MultipartFile[]::new), location);
    }

    /**
     * @param files
     * @param location
     * @return
     */
    public List<UploadFileResponse> storeGallaryImages(final MultipartFile[] files, final String location) {
        final List<UploadFileResponse> uploadMultipleFiles = new ArrayList<>();
        for (final MultipartFile file : files) {
            uploadMultipleFiles.add(this.storeGallaryImage(file, location, ""));
        }
        return uploadMultipleFiles;
    }

    /**
     * @param file
     * @param location
     * @return
     */
    public UploadFileResponse storeGallaryImage(final MultipartFile file, final String location,
                                                final String nameOfFile) {
        log.info("FILE : " + file);
        // Normalize file name
//		final String fileName = StringUtils.cleanPath(this.generateFileNameFromMultipart(file, nameOfFile));
        final String fileName = this.generateFileName() + "." + this.getExtensionOfFile(file);
        log.info("FILE_NAME : " + fileName);
//		location = location != null ? StringUtils.startsWithIgnoreCase(location, SLASH) ? location : SLASH + location
//				: "";
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            log.info("FILE_STORAGE_LOCN : " + this.fileStorageLocation);
            final Path rootDirectory = Paths.get(this.fileStorageLocation + "//" + location).toAbsolutePath()
                    .normalize();
            final Path targetLocation = Files.createDirectories(rootDirectory).resolve(fileName);
            log.info("TARGET_LOCN : " + targetLocation);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("file name : " + file.getOriginalFilename());
            return new UploadFileResponse(file.getOriginalFilename(), this.generateFileUri(location + SLASH + fileName),
                    file.getContentType(), file.getSize());
        } catch (final IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String getExtensionOfFile(MultipartFile file) {
        String fileExtension = "";
        // Get file Name first
        String fileName = file.getOriginalFilename();

        // If fileName do not contain "." or starts with "." then it is not a valid file
        if (fileName.contains(".") && fileName.lastIndexOf(".") != 0) {
            fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        }

        return fileExtension;
    }

}
