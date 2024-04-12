package com.dawnbit.entity.master;

import com.dawnbit.entity.master.OrganisationMaster;
import com.dawnbit.entity.master.Person;
import com.dawnbit.entity.master.Site;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "shipToBillTo")
@Data
public class ShipToBillTo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "address_code")
    private String addressCode;

    @JoinColumn(name = "orgId")
    @ManyToOne
    private OrganisationMaster org;


    @JoinColumn(name = "siteId")
    @ManyToOne
    private Site site;

    @Column(name = "billTo")
    private String billTo;

    @Column(name = "bill_to_contact")
    private String billToContact;

    @Column(name = "default_bill_to")
    private String defaultBillTo;

    @Column(name = "shipTo")
    private String shipTo;

    @Column(name = "ship_to_contact")
    private String shipToContact;

    @Column(name = "default_ship_to")
    private String defaultShipTo;





}
