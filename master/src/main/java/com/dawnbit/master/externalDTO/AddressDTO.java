package com.dawnbit.master.externalDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private long id;

    private String addressCode;

    private String address1;

    private String address2;

    private String city;

    private String country;

    private String region;

    private String state;

    private String org;

    private String pincode;

    private String landmark;

    private String status;

    private String description;
}
