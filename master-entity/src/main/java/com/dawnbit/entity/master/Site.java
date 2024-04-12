package com.dawnbit.entity.master;

import com.dawnbit.entity.master.common.utils.CommonUtils;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "site_details")
@Data
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @Column(name = "site_id")
//    private String siteId;

    @Column(name = "site_name")
    private String siteName;

    @JoinColumn(name = "orgId")
    @ManyToOne
    private OrganisationMaster org;


    @JoinColumn(name = "person")
    @ManyToOne
    private Employee person;

    @Column(name = "status")
    private String status;


    // --> User Activity Tracking
    @Column(name = "created_By")
    private String createdBy;

    @Column(name = "modified_By")
    private String modifiedBy;

    @Column(name = "created_Date")
    private Date createdDate;

    @Column(name = "modified_Date")
    private Date modifiedDate;

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
