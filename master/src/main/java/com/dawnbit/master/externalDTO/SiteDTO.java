package com.dawnbit.master.externalDTO;

import lombok.Data;

import java.util.List;

@Data
public class SiteDTO {

    private long id;

    private String siteId;

    private String siteName;

    private String orgId;

    private String person;

    private String address;

    private String billTo;

    private String shipTo;

    private String billToContact;

    private String shipToContact;

    private String description;

    private String status;

    private List<SiteAddressDto> siteAddress;
}
