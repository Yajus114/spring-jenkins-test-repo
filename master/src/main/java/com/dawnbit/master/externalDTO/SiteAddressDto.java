package com.dawnbit.master.externalDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SiteAddressDto {
    private String billTo;
    private String billToAddress1;
    private String billToAddress2;
    private String billToCity;
    private String billToContact;
    private String billToCountry;
    private String billToPincode;
    private String billToState;
    private String shipTo;
    private String shipToAddress1;
    private String shipToAddress2;
    private String shipToCity;
    private String shipToContact;
    private String shipToCountry;
    private String shipToPincode;
    private String shipToState;
}
