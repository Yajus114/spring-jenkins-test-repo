package com.dawnbit.entity.master;

import com.dawnbit.entity.master.common.utils.CommonUtils;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long employeeId;

    private String designation;

    private String employeeType;

    private String jobTitle;

    private Date hireDate;

    private Date terminationDate;

    private String experience;

    private String higherQualification;

    private String professionalQualification;

    @ManyToOne
    private OrganisationMaster organisationMaster;

    private String status;

    @ManyToOne
    private Person person;

    private String createdBy;

    private String modifiedBy;

    private Date createdDate;

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
