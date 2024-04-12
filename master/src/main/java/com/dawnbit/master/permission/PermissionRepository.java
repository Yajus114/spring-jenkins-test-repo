package com.dawnbit.master.permission;

import com.dawnbit.entity.master.Permission;
import com.dawnbit.master.external.dto.PermissionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query("SELECT DISTINCT new com.dawnbit.master.external.dto.PermissionDTO(p.id, p.name, p.moduleName,p.application,p.description,(Case when pg.permission is not null then"
            + " true else false end) as perChecked)	FROM Permission p left join PermissionGroupPermissions pg on p.id = pg.permission.id"
            + " AND pg.permissionGroup.id = ?1")
    List<PermissionDTO> findPermissionByPermissionGroupId(long id);

    @Query("SELECT DISTINCT new com.dawnbit.master.external.dto.PermissionDTO(p.id, p.name, p.moduleName,p.application,p.description,(Case when pg.permission is not null then"
            + " true else false end) as perChecked)	FROM Permission p left join PermissionGroupPermissions pg on p.id = pg.permission.id"
            + " AND pg.permissionGroup.id in ?1")
    List<PermissionDTO> findPermissionByPermissionGroupIds(List<Long> ids);

    @Query("SELECT DISTINCT new com.dawnbit.master.external.dto.PermissionDTO(p.id, p.name, p.moduleName,p.application,p.description,false) FROM Permission p left join PermissionGroupPermissions pg on p.id = pg.permission.id")
    List<PermissionDTO> findAllPermission();
}
