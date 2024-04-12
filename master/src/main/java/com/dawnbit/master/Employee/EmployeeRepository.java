package com.dawnbit.master.Employee;

import com.dawnbit.entity.master.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
//    List<Long> findAllByStatusIs(String status);

    /**
     * Find employees without user details.
     *
     * @return list of employees without user details
     * @author Ravi Kumar
     */
    @Query("SELECT e FROM Employee e WHERE e.employeeId NOT IN (?1) AND e.status = 'ACTIVE' AND e.organisationMaster.id=?2")
    List<Employee> findEmployeesWithoutUserDetails(List<Long> employeeIds, Long orgId);

    /**
     * Retrieves a list of employee IDs from the UserDetails table where the employee ID is not null.
     *
     * @return List of Long containing employee IDs
     * @author Ravi Kumar
     */
    @Query("SELECT ud.employee.employeeId FROM UserDetails ud where ud.employee.employeeId is not null")
    List<Long> getEmployeeIdsFromUserDetails();

    @Query("SELECT e FROM Employee e WHERE e.status = 'ACTIVE' AND e.organisationMaster.id=?1 ")
    List<Employee> findActiveEmployees(Long orgId);

    @Query(value = "select * from employee where organisation_master_id =?1", nativeQuery = true)
    List<Employee> fetchEmpByOrg(String organisationId);


//    List<EmployeeDropDownDTO> getEmployeeList(List<Long> employeeIds);
//    List<EmployeeDropDownDTO> find
}
