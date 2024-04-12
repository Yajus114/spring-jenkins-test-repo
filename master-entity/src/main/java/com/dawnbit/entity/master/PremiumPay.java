package com.dawnbit.entity.master;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PremiumPay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long premiumPayId;

    private String premiumPayCode;

    private String description;

    private Double standardRate;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private OrganisationMaster organisationMaster;

}
