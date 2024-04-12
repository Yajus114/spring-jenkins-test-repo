package com.dawnbit.master.group;

import com.dawnbit.entity.master.PermissionGroup;
import com.dawnbit.entity.master.UserDetails;
import com.dawnbit.master.external.dto.PermissionGroupPermissionDTO;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for group
 */
@Service
public interface GroupService {

    /**
     * mapping to save group
     *
     * @param group group object
     * @return saved/updated group object
     */
    PermissionGroup saveGroup(PermissionGroupPermissionDTO group) throws Exception;

    Page<?> fetchPermissionGroupInPageBySearchModel(SearchModelDTO searchModel);

    Object getRolesAndGroupByIdAndGroupedPermissions(long groupId);

    /**
     * mapping to update group
     *
     * @param group    group object
     * @param id       unique id of group
     * @param response response to be returned
     * @return map having saved/updated group
     * @throws Exception If group object is not found/request body is null
     */
    PermissionGroup updateGroup(long id, PermissionGroupPermissionDTO group, boolean changedDefault) throws Exception;

    /**
     * fetches the group object with its permissions in DTO by its id
     *
     * @param groupId group id
     * @return group object
     */
    PermissionGroupPermissionDTO getGroupByIdInDTO(long groupId);

    /**
     * Delete group record
     *
     * @param id group id
     * @return true or false
     * @throws Exception if record for given id is not found then exception is
     *                   thrown
     */
    boolean deleteGroup(long groupId) throws Exception;

    /**
     * check if default group exists in a role for the permission group to be saved
     *
     * @param roleId role id
     * @return boolean
     */
    boolean checkDefaultGroupExists(PermissionGroup group, long id);

    List<PermissionGroup> fetchUserType();

    /**
     * Assigns a permission group to a user.
     *
     * @param groupIds the ID of the permission group
     * @param userId   the ID of the user
     * @return the assigned permission group
     */
    void assignPermissionGroupToUser(List<Long> groupIds, long userId, String userKeywordId, String status);

    /**
     * Retrieves  grouped permissions for a list of group IDs.
     *
     * @param groupId List of group IDs
     * @return Object of type {@code Map<String, Object>} containing  the  grouped permissions
     * @author Ravi Kumar
     */
    Object getRolesAndGroupByIdAndGroupedPermissionsList(List<Long> groupId);

    List<UserDetails> checkKeywordDuplication(long employeeId, String keyword);
}
