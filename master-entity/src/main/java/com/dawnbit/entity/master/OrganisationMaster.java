package com.dawnbit.entity.master;

import com.dawnbit.entity.master.common.utils.CommonUtils;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class OrganisationMaster implements Serializable {

    @Serial
    private static final long serialVersionUID = -1339221866904838415L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "organisation_Id")
    private String organisationId;


    @Column(name = "organisation_Name")
    private String organisationName;


//    @Column(name = "key_word")
//    private String keyWord;

    @ManyToOne
    @JoinColumn(name = "currency")
    private Currency currency;

    @Column(name = "status")
    private String status;

    @Column(name = "logo")
    private String logo;


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


    public String getKeyWord() {
        return this.organisationId + " " + this.organisationName;
    }
}
