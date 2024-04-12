package com.dawnbit.master.group;

//import com.dawnbit.common.exception.CustomException;
//import com.dawnbit.common.service.RestServiceTemplateUtils;

import com.dawnbit.entity.master.PermissionGroup;
import com.dawnbit.master.external.dto.PermissionGroupPermissionDTO;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import com.dawnbit.master.userPermissionGroup.UserPermissisonGroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import io.swagger.annotations.ApiOperation;


@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/permissiongroup")
public class GroupController {
    /**
     * static variable to be used as key
     */
    private static final String GROUP = "permissiongroup";

    /**
     * injected GroupService
     */
    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserPermissisonGroupRepository userPermissisonGroupRepository;

    /**
     * Gets group page in search model
     *
     * @param searchModel searchModel
     * @return map
     */

//    @ApiOperation(value = "Get group in page by search model.", response = RestServiceTemplateUtils.class)
    @PostMapping("/getGroupInPageBySearchModel")
    public Map<String, Object> getGroupInPageBySearchModel(@RequestBody final SearchModelDTO searchModel) {
        final Map<String, Object> map = new HashMap<>();
        map.put(GROUP, groupService.fetchPermissionGroupInPageBySearchModel(searchModel));
        return map;
    }

    /**
     * fetches the list of roles,permission and a group by id
     *
     * @return map having roles
     */

//    @ApiOperation(value = "Get get All Roles, group object for a given id and grouped permission list.", response = RestServiceTemplateUtils.class)
    @GetMapping("/getRolesAndGroupByIdAndGroupedPermissions")
    public Map<String, Object> getRolesAndGroupByIdAndGroupedPermissions(@RequestParam long groupId) {
        final Map<String, Object> map = new HashMap<>();
        map.put(GROUP, groupService.getRolesAndGroupByIdAndGroupedPermissions(groupId));
        return map;
    }

    /**
     * fetches the group object by its id
     *
     * @param groupId group id
     * @return map having group object
     */
//    @ApiOperation(value = "Get group by id.", response = RestServiceTemplateUtils.class)
    @GetMapping("/getGroupById")
    public Map<String, Object> getGroupById(@RequestParam("id") final long groupId) {
        final Map<String, Object> map = new HashMap<>();
        map.put(GROUP, groupService.getGroupByIdInDTO(groupId));
        return map;
    }

    //    @ApiOperation(value = "Save group record ", response = HttpServletResponse.class)
    @PostMapping("/saveGroup")
    public Map<String, Object> saveGroup(@RequestBody PermissionGroupPermissionDTO group)
            throws Exception {
        final Map<String, Object> map = new HashMap<>();
        map.put(GROUP, groupService.saveGroup(group));
        return map;
    }

    //    @ApiOperation(value = "Update group.", response = RestServiceTemplateUtils.class)
    @PutMapping("updateGroup")
    public Map<String, PermissionGroup> updateGroup(@RequestParam final long id, @RequestBody final PermissionGroupPermissionDTO group, @RequestParam boolean changedDefault) throws Exception {
        final Map<String, PermissionGroup> map = new HashMap<>();
        map.put(GROUP, groupService.updateGroup(id, group, changedDefault));
        return map;
    }

    /**
     * Delete group record
     *
     * @param id group id
     * @return map
     * @throws Exception f record for given id is not found then exception is
     *                   thrown
     */
//    @ApiOperation(value = "Delete group by id", response = RestServiceTemplateUtils.class)
    @GetMapping("/deleteGroup")
    public ResponseEntity<Map<String, Boolean>> deleteGroup(@RequestParam("id") final long groupId)
            throws Exception {
        final Map<String, Boolean> map = new HashMap<>();
        map.put(GROUP, groupService.deleteGroup(groupId));
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    /**
     * check if default group exists in a role for the permission group to be saved
     *
     * @return map
     */
//    @ApiOperation(value = "Get get All Roles.", response = RestServiceTemplateUtils.class)
    @PutMapping("/checkDefaultGroupExists")
    public Map<String, Object> checkDefaultGroupExists(@RequestBody PermissionGroup group, @RequestParam(required = false) final long id) {
        final Map<String, Object> map = new HashMap<>();
        map.put(GROUP, groupService.checkDefaultGroupExists(group, id));
        return map;
    }

    //fetch usertype
    @GetMapping("/fetchUserType")
    public ResponseEntity<Map<String, Object>> fetchUserType() {
        final Map<String, Object> map = new HashMap<>();
        map.put("UserType", this.groupService.fetchUserType());
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    /**
     * Retrieves the permission group list.
     *
     * @return an ResponseEntity containing a map of permission group information
     * @author Ravi Kumar
     */
    @GetMapping("/getPermissionGroupList")
    public ResponseEntity<Map<String, Object>> getPermissionGroupList() {
        final Date startDate = new Date();
        final Map<String, Object> map = new HashMap<>();
        map.put(GROUP, this.groupRepository.fetchUserName());
        if (log.isInfoEnabled()) {
            log.info("Rest url /getPermissionGroupList from : ( " + startDate + " ) to : ( " + new Date()
                    + ")");
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    /**
     * Endpoint to assign a permission group to a user.
     *
     * @param groupIds the ID of the permission group
     * @param userId   the ID of the userDetails
     * @return a map containing the result of the assignment
     * @author Ravi Kumar & Yajus Gakhar
     */
    @GetMapping("/assignPermissionGroupToUser")
    public ResponseEntity<Object> assignPermissionGroupToUser(@RequestParam List<Long> groupIds, @RequestParam Long userId, @RequestParam String userKeywordId, @RequestParam String status) {
        try {
            final Date startDate = new Date();

            if (groupIds == null || groupIds.isEmpty() || userId == null || userId == 0) {
                throw new IllegalArgumentException("groupId or userId cannot be empty");
            }

            groupService.assignPermissionGroupToUser(groupIds, userId, userKeywordId, status);

            if (log.isInfoEnabled()) {
                log.info("Rest url /assignPermissionGroupToUser from : ( " + startDate + " ) to : ( " + new Date() + ")");
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves the assigned group for a specified user.
     *
     * @param userId the ID of the user
     * @return a ResponseEntity containing the assigned group for the user
     * @author Ravi Kumar
     */
    @GetMapping("/getAssignedGroupForUser")
    public ResponseEntity<Object> getAssignedGroupForUser(@RequestParam Long userId) {
        try {
            final Date startDate = new Date();

            if (userId == null || userId == 0) {
                throw new IllegalArgumentException("userId cannot be empty");
            }

            Map<String, Object> map = new HashMap<>();
            map.put("PERMISSIONGROUP", this.userPermissisonGroupRepository.findPermissionGroupByUserDetailsId(userId));

            if (log.isInfoEnabled()) {
                log.info("Rest url /getAssignedGroupForUser from : ( " + startDate + " ) to : ( " + new Date() + ")");
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves  grouped permissions for a list of group IDs.
     *
     * @param groupId List of group IDs
     * @return ResponseEntity with a map containing the  grouped permissions
     * @author Ravi Kumar
     */
    @GetMapping("/getRolesAndGroupByIdAndGroupedPermissionsList")
    public ResponseEntity<Object> getRolesAndGroupByIdAndGroupedPermissionsList(@RequestParam List<Long> groupId) {
        final Date startDate = new Date();
        Map<String, Object> map = new HashMap<>();
        map.put("GROUP", groupService.getRolesAndGroupByIdAndGroupedPermissionsList(groupId));

        if (log.isInfoEnabled()) {
            log.info("Rest url /getAssignedGroupForUser from : ( " + startDate + " ) to : ( " + new Date() + ")");
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/checkKeywordDuplication")
    public ResponseEntity<Map<String, String>> checkKeywordDuplication(@RequestParam String userKeywordId, @RequestParam String employeeId) {
        Map<String, String> map = new HashMap<>();
        if (userKeywordId.isEmpty() || employeeId.isEmpty()) {
            map.put("keyword", "emptyItems");
            return ResponseEntity.ok(map);
        }
        if (groupService.checkKeywordDuplication(Long.parseLong(employeeId), userKeywordId).isEmpty())
            map.put("keyword", "validKeyword");
        else
            map.put("keyword", "duplicateKeyword");
        return ResponseEntity.ok(map);
    }
}
