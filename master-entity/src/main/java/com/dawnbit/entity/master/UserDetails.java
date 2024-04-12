package com.dawnbit.entity.master;

import com.dawnbit.entity.master.common.utils.CommonUtils;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author DB-0085
 */

@Entity
@Data
@Table(name = "user_details")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userKeywordId;

    @Column(name = "user_name")
    private String userName;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private OrganisationMaster organisation;

    private String userType;

    private String address;

    private String emailId;

    private String contactNo;

    private String status;

    @ManyToOne
    @JoinColumn(name = "region_id")
    @NotNull
    private Region region;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @NotNull
    private Country country;

    @OneToOne
    @NotNull
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;

    private String createdBy;

    private Date createdDate;

    private String modifiedBy;

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
