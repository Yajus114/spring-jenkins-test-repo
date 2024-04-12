package com.dawnbit.master.externalDTO;

import lombok.Data;

@Data
public class OrgListDTO {
    private long id;
    private String organisationId;
    private String organisationName;
    private String keyWord;
    private String status;
    private Long currency;
    //    private String state;
//    private String city;
//    private String postCode;
//    private String address1;
//    private String address2;
//    private String emailId;
//    private String contactNumber;
    private String createdBy;
    private String modifiedBy;
    private String logo;
    private String description;
}
