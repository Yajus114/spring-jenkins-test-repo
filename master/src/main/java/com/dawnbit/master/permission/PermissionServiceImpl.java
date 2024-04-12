package com.dawnbit.master.permission;

import com.dawnbit.master.external.dto.PermissionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {

//    @Autowired
//    private GroupService groupService;

    @Autowired
    private PermissionRepository permissionRepo;

    @Override
    public Map<String, Map<String, List<PermissionDTO>>> getAllPermissionGroupByModuleGroupByApplication(long groupId) {
        final List<PermissionDTO> list;
        // log.info("getAllPermissionGroupByModuleGroupByApplication -> group id : "+groupId);
        if (groupId > 0) {
            list = permissionRepo.findPermissionByPermissionGroupId(groupId);
        } else {
            list = permissionRepo.findAllPermission();
        }
        if (!list.isEmpty()) {
            return list.stream().filter(p -> p.getModuleName() != null).collect(Collectors
                    .groupingBy(PermissionDTO::getModuleName, Collectors.groupingBy(PermissionDTO::getApplication)));
        }
        return new HashMap<>();
    }

    /**
     * Retrieves all permissions grouped by module and application based on the provided group IDs.
     * If group IDs are empty, retrieves all permissions.
     *
     * @param groupId List of group IDs
     * @return Map containing permissions grouped by module and application
     * @author Ravi Kumar
     */
    @Override
    public Map<String, Map<String, List<PermissionDTO>>> getAllPermissionGroupByModuleGroupByApplication(List<Long> groupId) {
        final List<PermissionDTO> list;

        // Check if group IDs are provided, if not fetch all permissions
        if (!groupId.isEmpty()) {
            list = permissionRepo.findPermissionByPermissionGroupIds(groupId);
        } else {
            list = permissionRepo.findAllPermission();
        }

        // Filter out permissions with null module names and group by module and application
        if (!list.isEmpty()) {
            return list.stream()
                    .filter(p -> p.getModuleName() != null)
                    .collect(Collectors.groupingBy(PermissionDTO::getModuleName, Collectors.groupingBy(PermissionDTO::getApplication)));
        }

        // Return an empty map if no permissions found
        return new HashMap<>();
    }
}
