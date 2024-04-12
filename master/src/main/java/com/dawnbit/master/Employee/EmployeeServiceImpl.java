package com.dawnbit.master.Employee;

import com.dawnbit.common.utils.PagingSortingUtils;
import com.dawnbit.entity.master.Employee;
import com.dawnbit.master.OrganisationMaster.OrganisationServiceImpl;
import com.dawnbit.master.externalDTO.EmployeeDTO;
import com.dawnbit.master.externalDTO.EmployeeDropDownDTO;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dawnbit.master.person.PersonServiceImpl.getObjects;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OrganisationServiceImpl organisationServiceImpl;

    @Override
    public Object getEmployeeById(String employeeId) {
        return employeeRepository.findById(Long.valueOf(employeeId));
    }

    @Override
    public Page<?> fetchEmployeeData(SearchModelDTO searchModelDTO) {
        int limit = searchModelDTO.getLimit();
        int offset = searchModelDTO.getOffset();
        String sortField = searchModelDTO.getSortingField();
        String sortDirection = searchModelDTO.getSortDirection();
        try (Session session = entityManager.unwrap(Session.class)) {
            if (sortField == null) {
                searchModelDTO.setSortingField("employee.employeeId");
            }
            if (sortDirection == null || sortDirection.isEmpty()) {
                searchModelDTO.setSortDirection("desc");
            }
            Map<String, Object> params = new HashMap<>();
            List<String> orgIds = organisationServiceImpl.getOrganizationIdsByUserRole();
            String hqlQuery = "select employee From Employee employee where employee.employeeId is not null";
            if (!orgIds.isEmpty()) {
                hqlQuery += " and employee.organisationMaster.id in (:orgIds)";
                params.put("orgIds", orgIds);
            }
            Map<String, Object> dataMap = PagingSortingUtils.fetchDataListWithUpdatedQueryStringInMaps(params, hqlQuery, searchModelDTO, session);
            Map<String, Object> resultmap = PagingSortingUtils.addPaginationDataInDataMap(dataMap, params, searchModelDTO, session, "employee");
            return getObjects(limit, offset, resultmap);
        }
    }

    @Override
    public Object updateEmployee(EmployeeDTO employeeDTO) {
        final Employee employee = employeeRepository.findById(employeeDTO.getEmployeeId()).orElse(null);
        if (employee != null) {
            employee.setStatus(employeeDTO.getEmployeeStatus().equalsIgnoreCase("true") ? "ACTIVE" : "INACTIVE");
            BeanUtils.copyProperties(employeeDTO, employee);
            employeeRepository.save(employee);
            return employeeDTO;
        }
        return null;
    }

    @Override
    public Object getEmployeeByOrgId(String organisationId) {
        return employeeRepository.fetchEmpByOrg(organisationId);
    }

    @Override
    public List<EmployeeDropDownDTO> findEmployeesWithoutUserDetails(Long orgId) {
        List<Long> employeeIds = employeeRepository.getEmployeeIdsFromUserDetails();
        List<EmployeeDropDownDTO> employeeDropDownDTOList = new ArrayList<>();
        List<Employee> employees;
        if (!employeeIds.isEmpty()) {
            employees = employeeRepository.findEmployeesWithoutUserDetails(employeeIds, orgId);
        } else {
            employees = employeeRepository.findActiveEmployees(orgId);
        }
        employees.forEach(employee -> {
            EmployeeDropDownDTO employeeDropDownDTO = new EmployeeDropDownDTO();
            employeeDropDownDTO.setEmployeeId(employee.getEmployeeId());
            employeeDropDownDTO.setEmployeeName(employee.getPerson().getFirstName() + " " + employee.getPerson().getLastName());
            employeeDropDownDTO.setEmployeeStatus(employee.getStatus());
            employeeDropDownDTOList.add(employeeDropDownDTO);
        });
        return employeeDropDownDTOList;
    }
}
