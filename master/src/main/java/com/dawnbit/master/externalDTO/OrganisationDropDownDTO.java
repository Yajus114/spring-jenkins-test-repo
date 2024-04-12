package com.dawnbit.master.externalDTO;

import com.dawnbit.entity.master.OrganisationMaster;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrganisationDropDownDTO {
    private long organisationId;

    private String organisationName;

    public OrganisationDropDownDTO(OrganisationMaster om) {
        this.organisationId = om.getId();
        this.organisationName = om.getOrganisationName();
    }
}
