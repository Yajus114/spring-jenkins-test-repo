package com.dawnbit.master.userPermissionGroup;

import com.dawnbit.entity.master.UserDetails;
import com.dawnbit.entity.master.UserPermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPermissisonGroupRepository extends JpaRepository<UserPermissionGroup, Long> {
    @Query(value = "SELECT * FROM user_permission_group where user_id=?1", nativeQuery = true)
    UserPermissionGroup findByUserId(long id);

    /**
     * Finds all user permission groups by user details.
     *
     * @param userDetails the user details to search by
     * @return a list of user permission groups
     */
    List<UserPermissionGroup> findAllByUserDetails(UserDetails userDetails);


    @Query("select upg.permissionGroup.id from UserPermissionGroup upg where upg.userDetails.id = ?1")
    List<Long> findPermissionGroupByUserDetailsId(Long userId);

//    @Query(value = "SELECT * FROM user_permission_group where user_id=?1", nativeQuery = true)
//    UserPermissionGroup findByUserIdRecord(long id);
}
