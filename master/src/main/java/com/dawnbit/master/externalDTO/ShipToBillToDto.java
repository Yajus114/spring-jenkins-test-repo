package com.dawnbit.master.externalDTO;

import lombok.Data;

@Data
public class ShipToBillToDto {
    private long id;
    private String addressCode;
    private String org;
    private String site;
    private String billTo;
    private String billToContact;
    private String defaultBillTo;
    private String shipTo;
    private String shipToContact;
    private String defaultShipTo;
}
