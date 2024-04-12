package com.dawnbit.master.external.dto;

import com.dawnbit.entity.master.PermissionGroup;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PermissionGroupPermissionDTO {
    String name;

    String description;

//	Role role;

    boolean defaultGroup;

    long organisation;

    List<PermissionDTO> permissionList;

    public PermissionGroupPermissionDTO(PermissionGroup permissionGroup, List<PermissionDTO> permissionList2) {
        name = permissionGroup.getName();
        description = permissionGroup.getDescription();

        permissionList = permissionList2;
    }
}
