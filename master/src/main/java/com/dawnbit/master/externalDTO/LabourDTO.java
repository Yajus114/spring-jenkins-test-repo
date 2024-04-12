package com.dawnbit.master.externalDTO;

import com.dawnbit.entity.master.Labour;
import com.dawnbit.entity.master.OrganisationMaster;
import com.dawnbit.entity.master.Person;
import com.dawnbit.master.externalDTO.dto.PersonAdditionalDataDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LabourDTO {

    private List<PersonAdditionalDataDTO> additionalData = new ArrayList<>();

    private Long labourId;

    private Long personId;

    private Long skillsId;

    private Long craftId;

    private String firstName;

    private String lastName;

    private boolean status;

    private String email;

    private Date dob;

    private String address;

    private String displayName;

    private String organisationId;

    private Long id;

    private String organisationName;

    private String phoneNumber;

    private String craftName;

    private String skillName;

    public LabourDTO(Labour labour, Person person, OrganisationMaster organisationMaster) {
        this.labourId = labour.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.personId = person.getId();
        this.displayName = person.getFirstName() + " " + person.getLastName();
        if (labour.getStatus() != null && labour.getStatus().equalsIgnoreCase("ACTIVE")) {
            this.status = true;
        } else {
            this.status = false;
        }
        this.email = person.getEmail();
        this.phoneNumber = person.getPhoneNumber();
        this.dob = person.getDob();
        if (labour.getOrganisationMaster() != null) {
            this.id = labour.getOrganisationMaster().getId();
            this.organisationId = String.valueOf(labour.getOrganisationMaster().getOrganisationId());
            this.organisationName = labour.getOrganisationMaster().getOrganisationName();
        }
    }
}
