package com.dawnbit.storage.attachments;

import com.dawnbit.entity.attachments.Attachments;

import lombok.Data;

/**
 * Attachment dto was created because I didn't want to change the naming on
 * UI.Perhaps it was a lengthy process.
 *
 * @author DB-0082
 */

@Data
public class AttachmentDTO {

    Long attachmentId;

    String documentName;

    String fileName;

    String documentUrl;

    public AttachmentDTO(final Attachments attachments) {
        super();
        this.attachmentId = attachments.getId();
        this.documentName = attachments.getName();
        this.documentUrl = attachments.getPath();
        this.fileName = attachments.getPath().substring(attachments.getPath().lastIndexOf('-') + 1);
    }

}
