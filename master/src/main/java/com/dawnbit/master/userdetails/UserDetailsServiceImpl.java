package com.dawnbit.master.userdetails;

import com.dawnbit.common.utils.ConstantUtils;
import com.dawnbit.common.utils.PagingSortingUtils;
import com.dawnbit.entity.master.*;
import com.dawnbit.master.Employee.EmployeeRepository;
import com.dawnbit.master.Mail.EmailService;
import com.dawnbit.master.OrganisationMaster.OrganisationService;
import com.dawnbit.master.commonNum.OrganisationUserNumRepository;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import com.dawnbit.master.externalDTO.UserDetailsDTO;
import com.dawnbit.master.group.GroupRepository;
import com.dawnbit.master.site.SiteRepo;
import com.dawnbit.master.userPermissionGroup.UserPermissisonGroupRepository;
import com.dawnbit.master.userlogin.UserLoginRepo;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    EmailService emailService;
    @Autowired
    private UserLoginRepo userLoginRepo;
    @Autowired
    private UserPermissisonGroupRepository userPermissisonGroupRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserDetailsRepo userDetailsRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OrganisationUserNumRepository organisationUserNumRepository;

    @Autowired
    private SiteRepo siteRepo;

//    @Autowired
//    private OrganisationRepository organisationRepository;
//
//    @Autowired
//    private CountryRepository countryRepository;

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private EmployeeRepository employeeRepository;

//    @Autowired
//    private PersonRepository personRepository;


    @Override
    public ResponseEntity<UserDetails> saveUserDetails(UserDetailsDTO userDetailsDTO) {
        try {
            Long employeeId = userDetailsDTO.getEmployeeId();
            String userKeywordId = userDetailsDTO.getUserKeywordId();
            List<Long> permissionGroupIds = userDetailsDTO.getPermissionGroupIds();
            // Retrieve the employee from the repository
            if (userDetailsDTO.getId() != null && userDetailsDTO.getId() != 0) {
                // update the User details
                UserDetails userDetails = this.userDetailsRepo.findByUserId(userDetailsDTO.getId());
                userDetails.setStatus(userDetailsDTO.getStatus().equalsIgnoreCase("true") ? "ACTIVE" : "INACTIVE");
                // Save the user details
                userDetails = userDetailsRepo.save(userDetails);

                // Create and set up the user login
                User userLogin = this.userLoginRepo.findByUserId(userDetails.getId());
                String generatedPassword = generateRandomPassword();
                userLogin.setUserId(userDetails.getId());
                userLogin.setUsername(userDetails.getUserName());
                userLogin.setPassword(passwordEncoder.encode(generatedPassword));
                userLogin.setName(userDetails.getUserKeywordId());
                userLogin.setRoles(userDetails.getUserType());

                // Save the user login
                userLoginRepo.save(userLogin);
                List<UserPermissionGroup> userPermissionGroupList = userPermissisonGroupRepository.findAllByUserDetails(userDetails);
                userPermissisonGroupRepository.deleteAll(userPermissionGroupList);

                // Assign permission groups to the user
                UserDetails finalUserDetails = userDetails;
                permissionGroupIds.forEach(permissionGroupId -> {
                    PermissionGroup permissionGroup = groupRepository.findById(permissionGroupId)
                            .orElseThrow(() -> new RuntimeException("Permission group not found"));

                    UserPermissionGroup userPermissionGroup = new UserPermissionGroup();
                    userPermissionGroup.setOrganisation(finalUserDetails.getOrganisation());
                    userPermissionGroup.setUserDetails(finalUserDetails);
                    userPermissionGroup.setPermissionGroup(permissionGroup);
                    userPermissisonGroupRepository.save(userPermissionGroup);
                });

                // Return the saved user details with HTTP status OK
                return new ResponseEntity<>(userDetails, HttpStatus.OK);
            } else {

                Employee employee = employeeRepository.findById(employeeId)
                        .orElse(null);
                // Create and set up the user details
                UserDetails userDetails = new UserDetails();
                userDetails.setUserKeywordId(userKeywordId.trim());
                userDetails.setUserName(employee.getPerson().getFirstName() + " " + employee.getPerson().getLastName());
                userDetails.setEmployee(employee);
                userDetails.setStatus(userDetailsDTO.getStatus().equalsIgnoreCase("true") ? "ACTIVE" : "INACTIVE");
                userDetails.setCountry(employee.getPerson().getCountry());
                userDetails.setAddress(employee.getPerson().getAddress1());
                userDetails.setEmailId(employee.getPerson().getEmail());
                userDetails.setContactNo(employee.getPerson().getPhoneNumber());
                userDetails.setOrganisation(employee.getOrganisationMaster());
                if (userDetailsDTO.getSiteId() != null && userDetailsDTO.getSiteId() != 0) {
                    Site site = this.siteRepo.findById(userDetailsDTO.getSiteId()).orElse(null);
                    userDetails.setSite(site);
                }
//                String userRoles =  String.join(",",userDetailsDTO.getUserRoles());
                userDetails.setUserType("EMPLOYEE");
                // Save the user details
                userDetails = userDetailsRepo.save(userDetails);
                // Create and set up the user login
                User userLogin = new User();
                String generatedPassword = generateRandomPassword();
                userLogin.setUserId(userDetails.getId());
                userLogin.setUsername(userDetails.getUserName());
                userLogin.setPassword(passwordEncoder.encode(generatedPassword));
                userLogin.setName(userDetails.getUserKeywordId());
                userLogin.setRoles(userDetails.getUserType());
                // Save the user login
                userLoginRepo.save(userLogin);
                List<UserPermissionGroup> userPermissionGroupList = userPermissisonGroupRepository.findAllByUserDetails(userDetails);
                userPermissisonGroupRepository.deleteAll(userPermissionGroupList);
                // Assign permission groups to the user
                UserDetails finalUserDetails = userDetails;
                permissionGroupIds.forEach(permissionGroupId -> {
                    PermissionGroup permissionGroup = groupRepository.findById(permissionGroupId)
                            .orElse(null);
                    UserPermissionGroup userPermissionGroup = new UserPermissionGroup();
                    userPermissionGroup.setOrganisation(finalUserDetails.getOrganisation());
                    userPermissionGroup.setUserDetails(finalUserDetails);
                    userPermissionGroup.setPermissionGroup(permissionGroup);
                    userPermissisonGroupRepository.save(userPermissionGroup);
                });
                // Update the user count for the organization
                OrganisationUserNum oun = organisationUserNumRepository.findByOrgKeywordIdIgnoreCase(
                        userDetails.getOrganisation().getOrganisationId());
                oun.setUserCount(oun.getUserCount() + 1);
                organisationUserNumRepository.save(oun);

                // Send mail to the user with login details
                sendMail(userDetails.getUserKeywordId(), generatedPassword, userDetails);

                // Return the saved user details with HTTP status OK
                return new ResponseEntity<>(userDetails, HttpStatus.OK);
            }

        } catch (Exception e) {
            // Return internal server error if an exception occurs
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //send email in background logic using executor
//    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
//
//    public void sendEmailInBackground(String userKeywordId, String generatedPassword, UserDetails userDetails) {
//        executor.submit(() -> {
//            // Send mail to the user with login details
//            sendMail(userKeywordId, generatedPassword, userDetails);
//        });
//    }

    private String generateRandomPassword() {
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters).concat(numbers);
        List<Character> pwdChars = combinedChars.chars().mapToObj(ch -> (char) ch).collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        return pwdChars.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }

//    public String createUserKeywordId(String orgId, String orgKeywordId) {
//        String userId;
//        OrganisationUserNum oun = this.organisationUserNumRepository.findByOrgKeywordIdIgnoreCase(orgId);
//        long userCount = oun.getUserCount() + 1;
//        if (userCount < 10) {
//            userId = orgKeywordId + "-00" + userCount;
//        } else if (userCount > 10 && userCount < 100) {
//            userId = orgKeywordId + "-0" + userCount;
//        } else {
//            userId = orgKeywordId + "-" + userCount;
//        }
//
//
//        return userId;
//
//    }

//    public PasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Override
    public List<UserDetails> getAllUser() {
        return userDetailsRepo.findAll();
    }

    @Override
    public Optional<UserDetails> getUserById(Long id) {

        return userDetailsRepo.findById(id);
    }

    public void sendMail(String userId, String password, UserDetails userDetails) {
        String firstName = userDetails.getUserName();
//        String appUrl = "https://staffmanagement.dawnbit.com";
        String appUrl = "https://multi-org-util.dawnbit.com";
        String recipient = userDetails.getEmailId();
        String subject = "User Login Credentials";
        String template = " Dear " + firstName + "," +
                "  your Multitenancy User profile has been successfully created.\n" +
                "Your login details are:" +
                " URL: " + appUrl +
                "\nYour UserId is " + userId + "\nYour Password is: " + password +
                "\nWarm Regards," +
                "\nDawnbit";

        this.emailService.sendEmail(recipient, subject, template);
    }

    public List<UserDetails> getUserBasedOnEmailId(String emailId, String orgId) {
        return this.userDetailsRepo.findByEmailAndOrganizationId(emailId, orgId);
    }

    @Override
    public Page<?> getUsersDetails(final SearchModelDTO searchModel) {
        List<String> orgIds = this.organisationService.getOrganizationIdsByUserRole();
        System.out.println("oganistaion list is : " + orgIds);
        Pageable pageable;
        final int limit = searchModel.getLimit();
        final int offset = searchModel.getOffset();
        final String sortField = searchModel.getSortingField();
        final String sortDirection = searchModel.getSortDirection();

        try (Session session = this.entityManager.unwrap(Session.class)) {
            // Construct the HQL query to fetch UserDetails entities with their associations
            final Map<String, Object> params = new HashMap<>();

            String hqlQuery = "SELECT ud "
                    + "FROM UserDetails ud where ud.userName is not null "
                    + "AND ud.id != :excludeId ";

            hqlQuery += " and ud.organisation.id in(:orgIds) ";

            params.put("orgIds", orgIds);
            params.put("excludeId", 1L);

            // Set default sorting field if not provided
            if (sortField == null) {
                searchModel.setSortingField("ud.id"); // Assuming 'id' is the default sorting field
            }
            // Set default sorting direction if not provided
            if (sortDirection == null || sortDirection.isEmpty()) {
                searchModel.setSortDirection("DESC"); // Assuming 'DESC' is the default sorting direction
            }

            // Execute the HQL query
//            final Map<String, Object> params = new HashMap<>();
            final Map<String, Object> dataMap = PagingSortingUtils.fetchDataListWithUpdatedQueryStringInMaps(params,
                    hqlQuery, searchModel, session);
            final Map<String, Object> resultMap = PagingSortingUtils.addPaginationDataInDataMap(dataMap, params,
                    searchModel, session, "ud");

            // Fetch the paginated data
            final List<?> searchedLists = (List<?>) resultMap.get(ConstantUtils.DATA_LIST);
            final long totalRows = (long) resultMap.get(ConstantUtils.TOTAL_ROWS);
            pageable = PageRequest.of(offset, limit);

            return new PageImpl<>(searchedLists, pageable, totalRows);
        }
    }

    @Override
    public Page<?> fetchUsersLoginData(final SearchModelDTO searchModel) {
//        List<String> orgIdList = this.organisationService.getOrganizationIdsByUserRole();
        Pageable pageable;
        final int limit = searchModel.getLimit();
        final int offset = searchModel.getOffset();
        final String sortField = searchModel.getSortingField();
        final String sortDirection = searchModel.getSortDirection();

        try (Session session = this.entityManager.unwrap(Session.class)) {
            // Construct the HQL query to fetch UserDetails entities with their associations
            final Map<String, Object> params = new HashMap<>();
            String hqlQuery = "SELECT ulh FROM UserLoginHistory ulh ";

            hqlQuery += " WHERE";

//			log.info("searchModel.getOrganizationId() ---" + searchModel.getOrganizationId());
            hqlQuery += " ulh.id IS NOT NULL ";

            // Assuming orgIdList is a List<Long> containing organization IDs
            params.put("params", params);

            // Set default sorting field if not provided
            if (sortField == null) {
                searchModel.setSortingField("ulh.id"); // Assuming 'id' is the default sorting field
            }
            // Set default sorting direction if not provided
            if (sortDirection == null || sortDirection.isEmpty()) {
                searchModel.setSortDirection("DESC"); // Assuming 'DESC' is the default sorting direction
            }

            // Execute the HQL query
//            final Map<String, Object> params = new HashMap<>();
            final Map<String, Object> dataMap = PagingSortingUtils.fetchDataListWithUpdatedQueryStringInMaps(params,
                    hqlQuery, searchModel, session);
            final Map<String, Object> resultMap = PagingSortingUtils.addPaginationDataInDataMap(dataMap, params,
                    searchModel, session, "ulh");

            // Fetch the paginated data
            final List<?> searchedLists = (List<?>) resultMap.get(ConstantUtils.DATA_LIST);
            final long totalRows = (long) resultMap.get(ConstantUtils.TOTAL_ROWS);
            pageable = PageRequest.of(offset, limit);

            return new PageImpl<>(searchedLists, pageable, totalRows);
        }
    }

    @Override
    public boolean isUserIdExists(String userId) {
        // Check if the user ID exists in the database
        return userDetailsRepo.existsByUserKeywordId(userId);
    }
//
//    @Override
//    public User findByNameId(String name) {
//        return userDetailsRepo.findByNameId(name);
//    }
}
