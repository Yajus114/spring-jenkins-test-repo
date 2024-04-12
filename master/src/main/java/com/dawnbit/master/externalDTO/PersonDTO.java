package com.dawnbit.master.externalDTO;

import com.dawnbit.entity.master.Employee;
import com.dawnbit.entity.master.Labour;
import com.dawnbit.entity.master.Person;
import com.dawnbit.master.externalDTO.dto.PersonAdditionalDataDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class PersonDTO {

    private Long id;

    private Long personId;

    private String firstName;

    private String lastName;

//    private boolean status;

    private String email;

    private String address1;

    private String address2;

    private String pinCode;

    private String phoneNumber;

    private Date dob;

    private Date hireDate;

    private Date terminationDate;

    private String jobTitle;

    private String designation;

    private String higherQualification;

    private String employeeType;

    private String professionalQualification;

    private String countryId;

    private String regionId;

    private String organisationId;

    private String organisationName;

    private String craftName;

    private String skillName;

    private String personFullName;

    private Boolean isEmployee;

    private boolean employeeStatus;

    private Boolean isLabour;

    private String state;

    private String city;

    private String experience;

    private List<PersonAdditionalDataDTO> additionalData;

    private Long labourId;

    private Long orgId;

    private Long employeeId;

    PersonDTO(Person person) {
        this.id = person.getId();
        this.personId = person.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.email = person.getEmail();
        this.address1 = person.getAddress1();
        this.address2 = person.getAddress2();
        this.pinCode = person.getPinCode();
        this.phoneNumber = person.getPhoneNumber();
        this.dob = person.getDob();
//        this.employeeType = person.getEmployeeType();
//        this.jobTitle = person.getJobTitle();
//        this.hireDate = person.getHireDate();
//        this.terminationDate = person.getTerminationDate();
        this.personFullName = person.getFirstName() + " " + person.getLastName();
    }

    PersonDTO(Person person, Employee e, Labour lb) {
        if (person != null) {
            this.id = person.getId();
            this.personId = person.getId();
            this.firstName = person.getFirstName();
            this.lastName = person.getLastName();
            this.state = person.getState();
            this.city = person.getCity();
            this.email = person.getEmail();
            this.address1 = person.getAddress1();
            this.address2 = person.getAddress2();
            this.pinCode = person.getPinCode();
            this.phoneNumber = person.getPhoneNumber();
            this.dob = person.getDob();
            this.isEmployee = person.getIsEmployee();
            this.isLabour = person.getIsLabour();
            this.personFullName = person.getFirstName() + " " + person.getLastName();
//            this.employeeStatus = e.getStatus();
        }

        if (e != null) {
            //employee fields
            this.employeeId = e.getEmployeeId();
            this.designation = e.getDesignation();
            this.orgId = e.getOrganisationMaster().getId();
            this.jobTitle = e.getJobTitle();
            this.employeeType = e.getEmployeeType();
            this.hireDate = e.getHireDate();
            this.terminationDate = e.getTerminationDate();
            this.experience = e.getExperience();
            this.higherQualification = e.getHigherQualification();
            this.professionalQualification = e.getProfessionalQualification();
        } else {
            this.employeeId = 0L;
        }
        if (lb != null) {
            //labour fields
            this.orgId = lb.getOrganisationMaster().getId();
            this.labourId = lb.getId();
        } else {
            this.labourId = 0L;
        }
    }
}
