package com.dawnbit.entity.master;

import com.dawnbit.entity.master.common.utils.CommonUtils;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String firstName;

    private String lastName;

    private String status;

    private String email;

    @ManyToOne
    @JoinColumn(name = "country_Id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "region_Id")
    private Region region;

//    @ManyToOne
//    private OrganisationMaster organisationMaster;

    private String address1;

    private String address2;

    private String pinCode;

    private String phoneNumber;

    private Date dob;

    private String state;

    private String city;

   // private Date hireDate;

  //  private Date terminationDate;

  //  private String jobTitle;

  //  private String employeeType;

   // private Boolean isUser;

    private Boolean isEmployee;

    private Boolean isLabour;

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
