package com.dawnbit.master.OrganisationMaster;


/**
 * @author DB-007
 */
public class UploadFileResponse {
    /**
     * fileName
     */
    private String fileName;
    /**
     * fileDownloadUri
     */
    private String fileDownloadUri;
    /**
     * fileType
     */
    private String fileType;
    /**
     * file size
     */
    private long size;

    /**
     * @param fileName
     * @param fileDownloadUri
     * @param fileType
     * @param size
     */
    public UploadFileResponse(final String fileName, final String fileDownloadUri, final String fileType,
                              final long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return this.fileDownloadUri;
    }

    public void setFileDownloadUri(final String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(final String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(final long size) {
        this.size = size;
    }
}
