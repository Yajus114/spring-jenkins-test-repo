package com.dawnbit.master.group;

import com.dawnbit.common.utils.CommonUtil;
import com.dawnbit.common.utils.ConstantUtils;
import com.dawnbit.common.utils.PagingSortingUtils;
import com.dawnbit.entity.master.*;
import com.dawnbit.master.Employee.EmployeeRepository;
import com.dawnbit.master.external.dto.PermissionDTO;
import com.dawnbit.master.external.dto.PermissionGroupPermissionDTO;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import com.dawnbit.master.permission.PermissionGroupPermissionsRepository;
import com.dawnbit.master.permission.PermissionRepository;
import com.dawnbit.master.permission.PermissionService;
import com.dawnbit.master.userPermissionGroup.UserPermissisonGroupRepository;
import com.dawnbit.master.userdetails.UserDetailsRepo;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepo;

    /**
     * injected EntityManager
     */
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PermissionGroupPermissionsRepository pgpRepo;

    /**
     * injected PermissionRepository
     */
    @Autowired
    private PermissionRepository permissionRepo;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserDetailsRepo userDetailsRepo;

    @Autowired
    private UserPermissisonGroupRepository userPermissisonGroupRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Page<?> fetchPermissionGroupInPageBySearchModel(final SearchModelDTO searchModel) {
        final int limit = searchModel.getLimit();
        final int offset = searchModel.getOffset();
        try (Session session = this.entityManager.unwrap(Session.class)) {
            final Map<String, Object> map = this.permissionGroupList(searchModel, session);
            /**
             * Fetch list
             */
            //System.out.println("map"+map);
            final List<?> searchedLists = (List<?>) map.get(ConstantUtils.DATA_LIST);
            final long totalRows = (long) map.get(ConstantUtils.TOTAL_ROWS);
            final Pageable pageable = PageRequest.of(offset, limit);
            return new PageImpl<>(searchedLists, pageable, totalRows);
        }
    }


    public Map<String, Object> permissionGroupList(final SearchModelDTO searchModel, final Session session) {
        final String sortField = searchModel.getSortingField();

        if (sortField == null) {
            searchModel.setSortingField("s.name");
        }

        /**
         * Fetch common query by calling below method
         */
        final Map<String, Object> queryMap = this.fetchMapForQueryAndParams(searchModel);
        final Map<String, Object> params = (Map<String, Object>) queryMap.get("params");

        final Map<String, Object> dataMap = PagingSortingUtils.fetchDataListWithUpdatedQueryStringInMaps(params, (String) queryMap.get("hqlQuery"), searchModel, session);
        //	System.out.println("data"+dataMap);

        return PagingSortingUtils.addPaginationDataInDataMap(dataMap, params, searchModel, session, "s");

    }

    public Map<String, Object> fetchMapForQueryAndParams(final SearchModelDTO searchModel) {
        final Map<String, Object> params = new HashMap<>();
        final Map<String, Object> queryMap = new HashMap<>();
        String hqlQuery = "SELECT s FROM PermissionGroup s";
        final Map<String, String> map = PagingSortingUtils.joinAndFetchFieldsForSorting(hqlQuery, searchModel);
        if (map != null) {
            hqlQuery = map.get(ConstantUtils.QUERY);
            searchModel.setSortingField(map.get(ConstantUtils.SORTING_ALIAS));
        }
        hqlQuery += " WHERE";

//			log.info("searchModel.getOrganizationId() ---" + searchModel.getOrganizationId());
        hqlQuery += " s.id IS NOT NULL ";


        queryMap.put("params", params);
        queryMap.put("hqlQuery", hqlQuery);

        return queryMap;
    }

    @Override
    public Object getRolesAndGroupByIdAndGroupedPermissions(final long groupId) {
        final Map<String, Object> map = new HashMap<>();
//		map.put("role", this.getAllRoles());
        map.put("permissiongroup", this.getGroupByIdInDTO(groupId));
        map.put("groupedPermission", this.permissionService.getAllPermissionGroupByModuleGroupByApplication(groupId));
        return map;
    }

    @Override
    public PermissionGroupPermissionDTO getGroupByIdInDTO(final long groupId) {
        final Optional<PermissionGroup> groupOpt = this.groupRepo.findById(groupId);
        if (groupOpt.isPresent()) {
            final List<PermissionDTO> permissionList = this.permissionRepo.findPermissionByPermissionGroupId(groupOpt.get().getId());
            return new PermissionGroupPermissionDTO(groupOpt.get(), permissionList);
        }
        return new PermissionGroupPermissionDTO();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public PermissionGroup saveGroup(final PermissionGroupPermissionDTO group) throws Exception {
        // check for duplicate entry
        final PermissionGroup existingData = this.groupRepo.findByName(group.getName());
        if (existingData != null) {
            throw new Exception("RECORD_ALREADY_EXISTS");
        }

        final PermissionGroup groupObject = new PermissionGroup(group.getName(), group.getDescription(), group.getOrganisation());


        this.groupRepo.save(groupObject);

        if ((group.getPermissionList() != null) && !group.getPermissionList().isEmpty()) {
            final List<PermissionGroupPermissions> pgpList = new ArrayList<>();
            new ArrayList<>();
            final List<PermissionDTO> permissionList = group.getPermissionList();
            if ((permissionList != null) && !permissionList.isEmpty()) {
                permissionList.forEach(permission -> {
                    final Permission permissionObj = new Permission(permission.getId(), permission.getName(), permission.getApplication(), permission.getModuleName(), permission.getDescription());
                    final PermissionGroupPermissions pgp = new PermissionGroupPermissions(groupObject, permissionObj);
                    pgpList.add(pgp);
                });

                this.pgpRepo.saveAll(pgpList);
            }
        } else {
            throw new RuntimeException("NO_PERMISSIONS_SELECTED");
        }
        return groupObject;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public PermissionGroup updateGroup(final long id, final PermissionGroupPermissionDTO group, final boolean changedDefault) throws Exception {
        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFF" + group.toString());
        if (id > 0) {
            final PermissionGroup existingData = this.groupRepo.findByName(group.getName());
            if ((existingData != null) && (existingData.getId() != id)) {
                throw new Exception("RECORD_ALREADY_EXISTS");
            } else {
//				if (changedDefault) {
//					this.groupRepo.updateByRoleIdAndDefaultGroupTrue(group.getRole().getId());
//				}
                final PermissionGroup groupObject = new PermissionGroup(group.getName(), group.getDescription(), group.getOrganisation());
                // update
                System.out.println("GGGGGGGGGGGGGGG");
                final Optional<PermissionGroup> groupOptional = this.groupRepo.findById(id);
                if (groupOptional.isPresent()) {
                    BeanUtils.copyProperties(groupObject, groupOptional.get(), CommonUtil.getNullPropertyNames(groupObject));
                    System.out.println("HHHHHHHHHHHHHH");
                    groupOptional.get().setId(id);
                    System.out.println("JJJJJJJJJJJJJJJJJJJ" + groupOptional.get());
                    // check if default exists or not
//					this.existsByRoleIdAndIsDefaultTrue(groupOptional.get(), id);
                    final PermissionGroup savedGroup = this.groupRepo.save(groupOptional.get());

                    pgpRepo.deleteByPermissionGroupId(groupOptional.get().getId());
                    System.out.println("RRRRRRR");
//					final Organization org = groupObject.getOrganization();

                    if (group.getPermissionList() == null) {
                        throw new Exception("NO_PERMISSIONS_SELECTED");
                    } else {
                        List<Long> permissionIdList = group.getPermissionList().stream().map(PermissionDTO::getId).collect(Collectors.toList());
                        List<Permission> permissionList = this.permissionRepo.findAllById(permissionIdList);

                        List<PermissionGroupPermissions> toAddpgpList = new ArrayList<>();
                        // get list of permission group permissions list to add
                        if (!permissionList.isEmpty()) {
                            permissionList.forEach(permission -> {
                                PermissionGroupPermissions pgp = new PermissionGroupPermissions(savedGroup, permission);
                                toAddpgpList.add(pgp);
                            });
                            pgpRepo.saveAll(toAddpgpList);
                        } else {
                            throw new Exception("NO_PERMISSIONS_SELECTED");
                        }

                        // System.out.println("existing
                        // permissions0------------"+pgpList.removeIf(item->
                        // existingPermissions.contains(item.getId()))+" to add permissios "+pgpList);
                        return groupOptional.get();

                    }
                } else {
                    throw new Exception("Not_Existing_Record");
                }
            }
        }
        return new PermissionGroup(null, null, id);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean deleteGroup(final long groupId) throws Exception {
        final Optional<PermissionGroup> groupOptional = this.groupRepo.findById(groupId);
        if (groupOptional.isPresent()) {
            if (groupOptional.get().isInUse()) {
//                log.info("delete throw exception");
                throw new Exception("GROUP_ASSOCIATED");
            } else {
//                log.info("in delete else no exception", groupId);
                // delete from permission group permission
                this.pgpRepo.deleteByPermissionGroupId(groupId);
                //		System.out.println("fdffdfdfdfd");
                // delete from group
                this.groupRepo.deleteById(groupId);
            }
            return true;
        } else {
            throw new Exception("Not_Existing_Record");
        }
    }

    @Override
    public boolean checkDefaultGroupExists(final PermissionGroup group, final long groupId) {
        if (groupId > 0) {
            final Optional<PermissionGroup> groupOptional = this.groupRepo.findById(groupId);
            if (groupOptional.isPresent()) {
                BeanUtils.copyProperties(group, groupOptional.get(), CommonUtil.getNullPropertyNames(group));
                groupOptional.get().setId(groupId);
            }
        }
        return false;
    }

    @Override
    public List<PermissionGroup> fetchUserType() {
        return this.groupRepo.fetchUserName();
    }

    /**
     * Assigns permission groups to a user.
     *
     * @param groupIds The list of group ids to assign to the user
     * @param userId   The id of the user
     * @author Ravi Kumar
     */
    @Override
    public void assignPermissionGroupToUser(List<Long> groupIds, long userId, String userKeywordId, String status) {
        // Find the user details
        Optional<UserDetails> optionalUserDetails = userDetailsRepo.findById(userId);

        // If the user details are present, assign permission groups
        optionalUserDetails.ifPresent(userDetails -> {
            // Find existing user permission groups and delete them
            List<UserPermissionGroup> userPermissionGroupList = userPermissisonGroupRepository.findAllByUserDetails(userDetails);
            userPermissisonGroupRepository.deleteAll(userPermissionGroupList);

            // If group ids are not empty, assign new permission groups
            if (groupIds != null && !groupIds.isEmpty()) {
                groupIds.forEach(groupId -> {
                    // Create a new user permission group
                    UserPermissionGroup userPermissionGroup = new UserPermissionGroup();
                    userDetails.setUserKeywordId(userKeywordId.trim());
                    userDetails.setStatus(status.equalsIgnoreCase("true") ? "ACTIVE" : "INACTIVE");
                    userPermissionGroup.setUserDetails(userDetails);
                    userPermissionGroup.setOrganisation(userDetails.getOrganisation());
                    userPermissionGroup.setPermissionGroup(groupRepo.findById(groupId).orElse(null));
//                    userPermissionGroup.getUserDetails().setUserKeywordId(userKeywordId);
                    userPermissisonGroupRepository.save(userPermissionGroup);
                });
            }
        });
    }

    /**
     * Retrieve group information, and grouped permissions based on a list of group IDs.
     *
     * @param groupId List of group IDs to retrieve information for
     * @return A map containing grouped permissions information
     * @author Ravi Kumar
     */
    @Override
    public Object getRolesAndGroupByIdAndGroupedPermissionsList(List<Long> groupId) {
        // Create a map to store the grouped permissions information
        final Map<String, Object> map = new HashMap<>();

        // Populate the map with groupedPermission key and the corresponding grouped permissions list
        map.put("groupedPermission", this.permissionService.getAllPermissionGroupByModuleGroupByApplication(groupId));

        // Return the map containing the grouped permissions information
        return map;
    }

    @Override
    public List<UserDetails> checkKeywordDuplication(long employeeId, String keyword) {
        // find organisation by employee id
        return userDetailsRepo.getUserDetailsByOrganisationAndUserKeywordId(employeeRepository.findById(employeeId).get().getOrganisationMaster(), keyword);
    }
}
