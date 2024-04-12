package com.dawnbit.master.Employee;

import com.dawnbit.master.externalDTO.EmployeeDTO;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * @param employeeId String value of the employee ID to display in the Employee Form
     * @return retrieved Employee Data
     * @author Yajus Gakhar
     */
    @GetMapping("/getEmployeeById")
    public ResponseEntity<Map<String, Object>> getEmployeeById(@RequestParam String employeeId) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", employeeService.getEmployeeById(employeeId));
        return ResponseEntity.ok(map);
    }

    @GetMapping("/fetchEmplyoeeByOrg/{organisationId}")
    public ResponseEntity<Map<String, Object>> fetchAddressOfOrg(@PathVariable String organisationId) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", employeeService.getEmployeeByOrgId(organisationId));
        return ResponseEntity.ok(map);
    }

    /**
     * A method to find employees which are not in user details
     *
     * @return ResponseEntity<Object> Response entity containing data of employees which are not in user details
     * @author Ravi Kumar
     */
    @GetMapping("/findEmployeesWithoutUserDetails")
    public ResponseEntity<Object> findEmployeesWithoutUserDetails(@RequestParam Long orgId) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", employeeService.findEmployeesWithoutUserDetails(orgId));
        return ResponseEntity.ok(map);
    }

    /**
     * @param searchModelDTO contains the searching parameters to fetch Employee Data through the SearchModel
     * @return Employee Data
     * @author Yajus Gakhar
     */
    @PostMapping("/fetchEmployeeData")
    public ResponseEntity<Map<String, Object>> fetchEmployeeData(@RequestBody SearchModelDTO searchModelDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", employeeService.fetchEmployeeData(searchModelDTO));
        return ResponseEntity.ok(map);
    }

    /**
     * @param employeeDTO contains information about the employee to update into the database
     * @return updated employee data
     * @author Yajus Gakhar
     */
    @PostMapping("/updateEmployee")
    public ResponseEntity<Map<String, Object>> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", employeeService.updateEmployee(employeeDTO));
        return ResponseEntity.ok(map);
    }
}
