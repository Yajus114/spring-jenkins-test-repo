package com.dawnbit.security.user;

import com.dawnbit.entity.master.UserPermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPermissionGroupRepository extends JpaRepository<UserPermissionGroup, Long> {
    /**
     * @param userId
     * @return
     */

    List<UserPermissionGroup> findByUserDetailsId(long userId);
}
