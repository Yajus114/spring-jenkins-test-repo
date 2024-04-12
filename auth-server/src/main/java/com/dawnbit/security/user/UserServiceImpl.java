package com.dawnbit.security.user;

import com.dawnbit.entity.master.User;
import com.dawnbit.entity.master.UserDetails;
import com.dawnbit.entity.master.UserLoginHistory;
import com.dawnbit.entity.master.UserPermissionGroup;
import com.dawnbit.security.repository.UserLoginHistoryRepository;
import com.dawnbit.security.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    /**
     * ROLE_PREFIX
     */
    private static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserPermissionGroupRepository userPermissionGroupRepository;
    @Autowired
    PermissionGroupPermissionsRepository pGPRepository;
    @Autowired
    UserLoginHistoryRepository userLoginHistoryRepository;
    @Autowired
    private UserDetailsRepo userDetailsRepo;

    @Override
    public UserDTO getLoggedInUser(final Principal principal, final HttpServletRequest request) {

        if (principal != null && principal.getName() != null && !StringUtils.isEmpty(principal.getName())) {
            final User user = this.userRepository.findByName(principal.getName()).get();
            final UserDetails userDetails = this.userDetailsRepo.findById(user.getUserId()).get();
            if (this.userDetailsRepo.findByIdAndStatus(user.getUserId(), "ACTIVE") != null) {
                final List<String> permissionList = this.getPermissionSet(user);
                //System.out.println("UserDTOshhhhhhhhhhhhhhhhh"+userDTO.toString());
                return new UserDTO(user, permissionList, userDetails);
            } else {
                throw new UsernameNotFoundException("The user is Inactive");
            }
        }
        throw new UsernameNotFoundException("The user doesn't exist");
    }

    @Override
    public void saveUserLoginHistory(String ip) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findByName(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserLoginHistory userLoginHistory = new UserLoginHistory();
            userLoginHistory.setIpAddress(ip);
            userLoginHistory.setUserKeywordId(username);
            userLoginHistory.setUserDetails(userDetailsRepo.findById(user.getId()).orElse(null));
            userLoginHistoryRepository.save(userLoginHistory);
        } else {
            throw new UsernameNotFoundException("The user doesn't exist");
        }
    }

//    @Override
//    public boolean checkFirstTimeLoggedInUser(Authentication authentication) {
//        String userName = authentication.getName();
//        User user = this.userRepository.findByName(userName).orElse(null);
//        if (user != null) {
//            if (!user.isNotFirstTimeLoggedIn()) {
//                user.setNotFirstTimeLoggedIn(true);
//                this.userRepository.save(user);
//            }
//        }
//        return false;
//    }

    /**
     * @param user
     * @author Sheetal Saini
     * @version 1.0
     * @description To get the permission List of logged in user return permission
     * list
     */
    private List<String> getPermissionSet(final User user) {
        //System.out.println("HHHHHHHHHHHHHHHHHHHHHH"+user.toString());
        final List<UserPermissionGroup> userPermissionGroupList = this.userPermissionGroupRepository
                .findByUserDetailsId(user.getUserId());
        //System.out.println("LLLLLLLLLLLLLuserPermissionGroupListLLLLLLLLLLLLLLLL"+userPermissionGroupList);
        final List<Long> permissionGroupIdList = userPermissionGroupList.stream()
                .map(userPermissionGroup -> userPermissionGroup.getPermissionGroup().getId())
                .collect(Collectors.toList());
        //System.out.println("LLLLLLXXXXLLLLLLpermissionGroupIdListLLLLLLLXXLLLLLLLLL"+permissionGroupIdList);
        final List<String> permissionNameList;
        permissionNameList = this.pGPRepository.findByPermissionGroupIn(permissionGroupIdList);
        //System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXX"+permissionNameList);
        permissionNameList.replaceAll(s -> s.startsWith(ROLE_PREFIX) ? s
                : ROLE_PREFIX + s);
        //System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYY"+permissionNameList);
        return permissionNameList;
    }
}
