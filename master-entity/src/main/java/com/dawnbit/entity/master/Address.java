package com.dawnbit.entity.master;

import com.dawnbit.entity.master.common.utils.CommonUtils;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "addressCode")
    private String addressCode;


    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "city")
    private String city;

    @ManyToOne
    @JoinColumn(name = "country")
    private Country country;

    @Column(name = "state")
    private String state;

    @JoinColumn(name = "org")
    @ManyToOne
    private OrganisationMaster org;

    @Column(name = "pincode")
    private String pincode;


    @Column(name = "description")
    private String description;

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
