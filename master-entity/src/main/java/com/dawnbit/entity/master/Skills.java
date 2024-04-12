package com.dawnbit.entity.master;

import com.dawnbit.entity.master.common.utils.CommonUtils;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;

    private String skillName;

    private String description;

    private Integer skillLevel;

    private String types;

    @ManyToOne
    @JoinColumn(name = "craft_id")
    private Craft craft;

    private Double standardRate;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private OrganisationMaster organisationMaster;

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
