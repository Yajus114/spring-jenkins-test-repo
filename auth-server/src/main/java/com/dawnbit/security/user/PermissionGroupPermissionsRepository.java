package com.dawnbit.security.user;

import com.dawnbit.entity.master.PermissionGroupPermissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionGroupPermissionsRepository extends JpaRepository<PermissionGroupPermissions, Long> {
    /**
     * @param permissionGroupId
     * @return
     */
    @Query("select distinct(pgp.permission.name) from PermissionGroupPermissions pgp where pgp.permissionGroup.id in (?1)")
    List<String> findByPermissionGroupIn(List<Long> permissionGroupId);
}
