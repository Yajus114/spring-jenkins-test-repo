package com.dawnbit.master.permission;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/permission")
public class PermissionController {
//    private static final String TO = " ) to : ( ";
//
//    private static final String PERMISSION = "Permission";

    @Autowired
    private PermissionService permissionService;

    /**
     * @return map
     * @author DB-0083
     * <p>
     * Get all permission list grouped by application name and list of
     * distinct modules.
     */
//    @ApiOperation(value = "Get all permission list grouped by application name and list of distinct modules.", response = RestServiceTemplateUtils.class)
    @GetMapping("getDistinctModulesAndPermissionsGroupByApplication")
    public Map<String, Object> getDistinctModulesAndPermissionsGroupByApplication() {
        //        map.put("permissions", permissionService.getAllPermissionGroupByModuleGroupByApplication(0));
        return new HashMap<>();
    }
}
