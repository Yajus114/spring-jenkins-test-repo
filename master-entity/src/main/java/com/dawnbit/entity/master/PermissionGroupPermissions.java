package com.dawnbit.entity.master;

import com.dawnbit.entity.master.common.utils.CommonUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author DB-0079
 */
@Data
@Table(name = "permission_group_permissions")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PermissionGroupPermissions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "permission_group_id")
    private PermissionGroup permissionGroup;

    @ManyToOne
    @JoinColumn(name = "permission_id")
    private Permission permission;

    private String modifiedBy;

    private String createdBy;

    private Date createdDate;

    private Date modifiedDate;


    public PermissionGroupPermissions(final PermissionGroup permissionGroup, final Permission permission) {
        super();
        this.permissionGroup = permissionGroup;
        this.permission = permission;

    }

    @PrePersist
    private void persistDates() {
        this.createdDate = new Date();
        this.modifiedDate = new Date();
        this.createdBy = CommonUtils.getPrincipal();
        this.modifiedBy = CommonUtils.getPrincipal();
    }

    @PreUpdate
    private void updateDates() {
        this.modifiedDate = new Date();
        this.modifiedBy = CommonUtils.getPrincipal();
    }

}
