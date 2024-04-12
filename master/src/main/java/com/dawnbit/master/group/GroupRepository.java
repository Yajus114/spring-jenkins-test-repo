package com.dawnbit.master.group;

import com.dawnbit.entity.master.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<PermissionGroup, Long> {

    /**
     * checks if group name exists already within an organization
     *
     * @param name group name
     * @return group object
     */
    PermissionGroup findByName(String name);

    @Query(value = " select * from permission_group order by name", nativeQuery = true)
    List<PermissionGroup> fetchUserName();
}
