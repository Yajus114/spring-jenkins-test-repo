package com.dawnbit.master.Employee;

import com.dawnbit.master.externalDTO.EmployeeDTO;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {

    Object getEmployeeById(String employeeId);

    Object findEmployeesWithoutUserDetails(Long orgId);

    Page<?> fetchEmployeeData(SearchModelDTO searchModelDTO);

    Object updateEmployee(EmployeeDTO employeeDTO);

    Object getEmployeeByOrgId(String organisationId);
}
