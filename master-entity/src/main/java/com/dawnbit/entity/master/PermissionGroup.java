package com.dawnbit.entity.master;

import com.dawnbit.entity.master.common.utils.CommonUtils;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * @author DB-0079
 */
@Data
@Table(name = "permission_group")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PermissionGroup {

    /**
     * Primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * name of the permission group
     */
    private String name;

    /**
     * permission group description
     */
    private String description;

    /**
     * true if permission group is associated with permission
     */
    private boolean isInUse;

    /**
     * Reference to roles
     */


    /**
     * Whether a default group or not
     */
    private boolean defaultGroup;

    /**
     * Created By User
     */
    @Setter(AccessLevel.NONE)
    private String createdBy;

    /**
     * Created Date-time of record
     */
    @Setter(AccessLevel.NONE)
    private Date createdDate;

    /**
     * Modified By User
     */
    @Setter(AccessLevel.NONE)
    private String modifiedBy;

    /**
     * Modified Date-time of record
     */
    @Setter(AccessLevel.NONE)
    private Date modifiedDate;


    @JoinColumn(name = "organisation_id")
    private long organisation;

    public PermissionGroup(final String name, final String description, final long organisation
    ) {
        super();
        this.name = name;
        this.description = description;
        this.organisation = organisation;
    }

    /**
     * To create record for who created data and when
     */
    @PrePersist
    private void persistDates() {
        this.createdDate = new Date();
        this.modifiedDate = new Date();
        this.createdBy = CommonUtils.getPrincipal();
        this.modifiedBy = this.createdBy;
    }

    /**
     * To create record when the data was updated last and by whom
     */
    @PreUpdate
    private void updateDates() {
        this.modifiedDate = new Date();
        this.modifiedBy = CommonUtils.getPrincipal();
    }

}
