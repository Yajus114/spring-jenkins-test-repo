package com.dawnbit.master.externalDTO;

import com.dawnbit.entity.master.Region;
import lombok.Data;

import java.util.List;

@Data
public class UserDetailsDTO {

    private Long id;
    private String userKeywordId;

    private long employeeId;

    private String userName;

    private List<Long> permissionGroupIds;

    private String userId;

    private Long organisationId;
    private String userType;
    private List<String> userRoles;
    private String address;
    private String emailId;
    private String contactNo;
    private String status;

    private Region region;

    private Long siteId;

    private long countryId;

    private long regionId;


}
