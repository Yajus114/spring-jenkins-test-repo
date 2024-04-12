package com.dawnbit.security.user;

import com.dawnbit.entity.master.User;
import com.dawnbit.entity.master.UserDetails;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

    /**
     * login user name
     */
    @NotEmpty
    private String username;

    /**
     * user id
     */
    private long userId;

    /**
     * User Type id
     */
    private long userTypeId;

    /**
     * is user enabled
     */
    private boolean isEnabled;

    private boolean restricted;

    private String name;

    private Long organisationId;
    private String organisationLogo;
    /**
     * password
     */
    @NotEmpty
    private String password;

    /**
     * role type of user
     */
    private String roles;

    private boolean isNotFirstTimeLoggedIn;


    /**
     * @param user
     * @param permissionList
     */
    private List<String> permissionList = new ArrayList<>();

    /**
     * @param user
     * @param permissionList
     */
    public UserDTO(final User user, final List<String> permissionList, final UserDetails userDetails) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.permissionList = permissionList;
        this.roles = user.getRoles();
        this.isNotFirstTimeLoggedIn = user.isNotFirstTimeLoggedIn();
        this.isEnabled = user.isEnabled();
        this.userTypeId = user.getUserId();
        this.restricted = user.isRestricted();
        this.name = user.getName();
        this.organisationId=userDetails.getOrganisation().getId();
        this.organisationLogo=userDetails.getOrganisation().getLogo();
     }
}
