package com.dawnbit.master.externalDTO;

import com.dawnbit.entity.master.*;
import com.dawnbit.master.external.dto.QueryDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
public class SearchModelDTO {

    private String organisationId;

    private String organisationUniqueId;

    private String organisationName;

    private String groupId;

    private String personId;

    private String labourId;

    private String firstName;

    private String lastName;

    private String status;

    private String email;

    private String address;

    private String pinCode;

    private String phoneNumber;

    private Date dob;

    private Date hireDate;

    private Date terminationDate;

    private String jobTitle;

    private String employeeType;

    private String skillName;

    private String craft;

    private String person;

    private String craftName;

    private String searchField;

    private String searchValue;

    private int limit;

    private int offset;

    private String sortingField;

    private String sortDirection;

    private String havingField;

    private String havingValue;

    private String havingString;

    private String havingAlternate;

    private String groupByField;

    private String siteName;

    private String siteId;

    private String orgId;

    private String regionId;

    private Map<String, QueryDTO> filterFieldsWithValue;

    private String keyWord;

    private Long countryId;

    private String emailId;

    private String org;

//    private Person person;

    private String addressId;

    private String address1;

    private String address2;

    private String city;

    private String country;

    private String region;

    private String state;

    private String pincode;

    private String landmark;

    private String currency;

//    private String status;


    SearchModelDTO(Person person) {
        this.personId = String.valueOf(person.getId());
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.email = person.getEmail();
        this.phoneNumber = person.getPhoneNumber();
//        this.employeeType = person.getEmployeeType();
//        this.jobTitle = person.getJobTitle();
//        this.status = person.getStatus();
//        this.hireDate = person.getHireDate();
//        this.terminationDate = person.getTerminationDate();
    }

    SearchModelDTO(Person person, Labour labour, Employee employee, OrganisationMaster organisationMaster) {
        this.personId = String.valueOf(person.getId());
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.email = person.getEmail();
        this.phoneNumber = person.getPhoneNumber();
        if (person.getIsLabour()) {
            this.organisationName = labour != null ? labour.getOrganisationMaster().getOrganisationName() : null;
        } else if (person.getIsEmployee()) {
            this.organisationName = employee != null ? employee.getOrganisationMaster().getOrganisationName() : null;
        } else {
            this.organisationName = null;
        }

//        this.employeeType = person.getEmployeeType();
//        this.jobTitle = person.getJobTitle();
//        this.status = person.getStatus();
//        this.hireDate = person.getHireDate();
//        this.terminationDate = person.getTerminationDate();
    }

    SearchModelDTO(OrganisationMaster organisationMaster) {
        this.organisationId = String.valueOf(organisationMaster.getId());
        this.organisationUniqueId = organisationMaster.getOrganisationId();
        this.organisationName = organisationMaster.getOrganisationName();
        this.status = organisationMaster.getStatus();
        Currency currency = organisationMaster.getCurrency();
        if (currency != null) {
            this.currency = currency.getCurrency();
        }
//        Country country = organisationMaster.getCountry();
//        if (country != null) {
//            this.countryId = country.getId();
//        }
    }

    SearchModelDTO(Labour labour, Person person, OrganisationMaster organisationMaster) {
        this.personId = String.valueOf(person.getId());
        this.labourId = String.valueOf(labour.getId());
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.status = labour.getStatus();
        this.organisationId = organisationMaster.getOrganisationId();
        this.organisationName = organisationMaster.getOrganisationName();
        this.email = person.getEmail();

        System.out.println("--------organisationId-------------" + organisationId);

    }
}
