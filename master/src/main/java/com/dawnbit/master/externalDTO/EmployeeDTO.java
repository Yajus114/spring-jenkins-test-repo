package com.dawnbit.master.externalDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class EmployeeDTO {
    private Long employeeId;
    private String designation;
    private String employeeType;
    private String jobTitle;
    private Date hireDate;
    private Date terminationDate;
    private String experience;
    private String higherQualification;
    private String professionalQualification;
    private String organisationMaster;
    private String employeeStatus;
    private String person;
    private String createdBy;
    private String modifiedBy;
    private Date createdDate;
    private Date modifiedDate;

    EmployeeDTO(EmployeeDTO employeeDTO) {
        this.employeeId = employeeDTO.getEmployeeId();
        this.designation = employeeDTO.getDesignation();
        this.employeeType = employeeDTO.getEmployeeType();
        this.jobTitle = employeeDTO.getJobTitle();
        this.hireDate = employeeDTO.getHireDate();
        this.terminationDate = employeeDTO.getTerminationDate();
        this.experience = employeeDTO.getExperience();
        this.higherQualification = employeeDTO.getHigherQualification();
        this.professionalQualification = employeeDTO.getProfessionalQualification();
        this.organisationMaster = employeeDTO.getOrganisationMaster();
        this.employeeStatus = employeeDTO.getEmployeeStatus();
        this.person = employeeDTO.getPerson();
        this.createdBy = employeeDTO.getCreatedBy();
        this.modifiedBy = employeeDTO.getModifiedBy();
        this.createdDate = employeeDTO.getCreatedDate();
        this.modifiedDate = employeeDTO.getModifiedDate();
    }
}