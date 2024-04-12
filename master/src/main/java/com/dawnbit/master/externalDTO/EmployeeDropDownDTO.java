package com.dawnbit.master.externalDTO;

import com.dawnbit.entity.master.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeDropDownDTO {

    private Long employeeId;
    private String employeeName;
    private String status;

    public EmployeeDropDownDTO(Employee e) {
        this.employeeId = e.getEmployeeId();
        if (e.getPerson() != null) this.employeeName = e.getPerson().getFirstName() + " " + e.getPerson().getLastName();
        this.status = e.getStatus();
    }

    public void setEmployeeStatus(String status) {
        this.status = status;
    }
}
