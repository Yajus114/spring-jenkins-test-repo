package com.dawnbit.entity.attachments;

import java.io.Serializable;
import java.util.Date;


//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DB-0007
 */
@Entity
@Table(name = "attachments")
@NoArgsConstructor
@Data
public class Attachments implements Serializable {

    private static final long serialVersionUID = -6475811173554369739L;

    /**
     * unique id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Name
     */
    @Column(name = "name")
    private String name;

    /**
     * File Type
     */
    @Column(name = "type")
    private String type;

    /**
     * File Size
     */
    @Column(name = "size")
    private long size;

    /**
     * File URL Location of the file
     */
    @Column(name = "path")
    private String path;

    /**
     * File URL Location of the file
     */
    @Column(name = "directory_location")
    private String directoryLocation;

    /**
     * Reference Entity Name Entity name for which file have been saved
     */
    @Column(name = "entity")
    private String entity;

    /**
     * Modified By User Modified By User
     */
    @Column(name = "modified_by")
    private String modifiedBy;

    /**
     * Created By User Created By User
     */
    @Column(name = "created_by")
    private String createdBy;

    /**
     * Created Datetime of record
     */
    @Column(name = "created")
    private Date created;

    /**
     * Modified Datetime of record
     */
    @Column(name = "modified")
    private Date modified;

    /**
     * organization code for Multitenancy 
     */
    @Column(name = "org_code")
    private String org_code;

    public Attachments(final String fileDownloadUri, final String fileName, final String fileType, final long fileSize,
                       final String entity, final String directoryLocation, final String organization) {
        this.path = fileDownloadUri;
        this.name = fileName;
        this.size = fileSize;
        this.type = fileType;
        this.entity = entity;
        this.directoryLocation = directoryLocation;
        this.org_code = organization;
    }

    /**
     * to create record for who created data and when
     */
    @PrePersist
    public void prePersist() {
        this.created = new Date();
        this.modified = this.created;
        this.createdBy = this.getUsernameFromPrincipal();
        this.modifiedBy = this.createdBy;
    }

    /**
     * to create record when the data was updated last and by whom
     */
    @PreUpdate
    public void preUpdate() {
        this.modified = new Date();
        this.modifiedBy = this.getUsernameFromPrincipal();
    }

    /**
     * get username from principal
     *
     * @return username from principal
     * @author DB-0007
     */
    private String getUsernameFromPrincipal() {
		/* if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
			final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				return ((UserDetails) principal).getUsername();
			}
			return principal.toString();
		} */
        return null;
    }

}
