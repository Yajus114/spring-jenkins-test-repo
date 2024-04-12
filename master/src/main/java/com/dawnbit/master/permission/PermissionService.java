package com.dawnbit.master.permission;

import com.dawnbit.master.external.dto.PermissionDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface PermissionService {

    /**
     * Get all permission list grouped by application name and list of
     * distinct modules.
     *
     * @param groupId
     * @return map
     */
    Map<String, Map<String, List<PermissionDTO>>> getAllPermissionGroupByModuleGroupByApplication(long groupId);

    Map<String, Map<String, List<PermissionDTO>>> getAllPermissionGroupByModuleGroupByApplication(List<Long> groupId);
}
