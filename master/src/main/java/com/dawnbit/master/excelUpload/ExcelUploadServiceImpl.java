package com.dawnbit.master.excelUpload;

import com.dawnbit.common.utils.ConstantUtils;
import com.dawnbit.common.utils.PagingSortingUtils;
import com.dawnbit.common.utils.StringUtils;
import com.dawnbit.common.utils.excel.ErrorConstant;
import com.dawnbit.common.utils.excel.FieldError;
import com.dawnbit.entity.master.*;
import com.dawnbit.master.OrganisationMaster.OrganisationRepository;
import com.dawnbit.master.commonNum.OrganisationUserNumRepository;
import com.dawnbit.master.country.CountryRepository;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import com.dawnbit.master.group.GroupRepository;
import com.dawnbit.master.userPermissionGroup.UserPermissisonGroupRepository;
import com.dawnbit.master.userdetails.UserDetailsRepo;
import com.dawnbit.master.userdetails.UserDetailsServiceImpl;
import com.dawnbit.master.userlogin.UserLoginRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExcelUploadServiceImpl implements ExcelUploadService {
    @Autowired
    UserSetupRepository userSetupRepository;

    @Autowired
    OrganisationUserNumRepository organisationUserNumRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    UserDetailsRepo userDetailsRepo;

    @Autowired
    UserLoginRepo userLoginRepo;

    @Autowired
    OrganisationRepository organisationRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    UserPermissisonGroupRepository userPermissisonGroupRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private EntityManager entityManager;

    private void deleteAllUserSetupTemp() {
        this.userSetupRepository.deleteAll();

    }

    @Override
    public long readAndProcessUserData(MultipartFile multipartFile, Map<String, Object> map) {
        try {
            this.deleteAllUserSetupTemp();
            final InputStream stream = multipartFile.getInputStream();
            final XSSFWorkbook workbook = new XSSFWorkbook(stream);
            if (workbook.getNumberOfSheets() > 0) {

                // Step 1 :=> Process First Sheet
                final XSSFSheet sheet = workbook.getSheetAt(1);
                final List<UserSetupTemp> userSetupTempList = new ArrayList<>();

                final List<Map<Integer, String>> excelList = new ArrayList<>(com.dawnbit.common.utils.excel.Excel.getSheetData(sheet, 1));
                final Map<Integer, String> headerRow = com.dawnbit.common.utils.excel.Excel.getHeaderRow(sheet);
                final long processId = new Date().getTime();
                final int excelListSize = excelList.size();
                try {
                    if (excelListSize > 0) {
                        for (final Map<Integer, String> currentRow : excelList) {
                            if (currentRow != null && currentRow.get(0) != null
                                    && !currentRow.get(0).equalsIgnoreCase("")) {
                                final String userName = currentRow.get(0);
                                final String userType = currentRow.get(1);
                                final String organisation = currentRow.get(2);
                                final String contactNo = currentRow.get(3);
                                final String email = currentRow.get(4);
                                final String country = currentRow.get(5);
                                final String status = currentRow.get(6);
                                final String address = currentRow.get(7);
                                final List<com.dawnbit.common.utils.excel.FieldError> fieldErrorList = new ArrayList<>();
                                final UserSetupTemp userSetupTemp = new UserSetupTemp(
                                        processId, userName,
                                        userType, organisation, contactNo, email,
                                        status, country, address);
                                if (StringUtils.isBlank(userName)) {
                                    fieldErrorList.add(new FieldError(headerRow.get(0), userName,
                                            ErrorConstant.BLANK_FIELD));
                                }
                                if (StringUtils.isBlank(userType)) {
                                    fieldErrorList.add(new FieldError(headerRow.get(1), userType,
                                            ErrorConstant.BLANK_FIELD));
                                }
                                if (StringUtils.isBlank(contactNo)) {
                                    fieldErrorList.add(
                                            new FieldError(headerRow.get(3), contactNo, ErrorConstant.BLANK_FIELD));
                                }
                                if (StringUtils.isBlank(email)) {
                                    fieldErrorList
                                            .add(new FieldError(headerRow.get(4), email, ErrorConstant.BLANK_FIELD));
                                }
                                if (StringUtils.isBlank(address)) {
                                    fieldErrorList
                                            .add(new FieldError(headerRow.get(7), address, ErrorConstant.BLANK_FIELD));
                                }
                                if (StringUtils.isBlank(country)) {
                                    fieldErrorList.add(
                                            new FieldError(headerRow.get(5), country, ErrorConstant.BLANK_FIELD));
                                } else {
                                    final Country countryName = this.countryRepository.findByCountryName(country);
                                    if (countryName != null) {
                                        userSetupTemp.setCountry(country);
                                    } else {
                                        fieldErrorList.add(new FieldError(headerRow.get(5), country,
                                                ErrorConstant.INVALID_VALUE));
                                    }
                                }
                                if (StringUtils.isBlank(status)) {
                                    fieldErrorList
                                            .add(new FieldError(headerRow.get(6), email, ErrorConstant.BLANK_FIELD));
                                }
                                if (StringUtils.isBlank(organisation)) {
                                    fieldErrorList
                                            .add(new FieldError(headerRow.get(2), organisation,
                                                    ErrorConstant.BLANK_FIELD));
                                } else {

                                    String loggedInuserName = SecurityContextHolder.getContext().getAuthentication().getName();
                                    System.out.println("--------------------loggedInuserName---------" + loggedInuserName);
                                    User user = this.userLoginRepo.findUser(loggedInuserName);
                                    final UserDetails loggedInUser = this.userDetailsRepo.findByIds(user.getUserId());
                                    System.out.println("==================loggedInUser=============" + loggedInUser.getUserType());
//                                    List<String> orgIds = this.organisationService.getOrganizationIdsByUserRole();
                                    OrganisationMaster orgObj = this.organisationRepository.findByOrganisationNameIgnoreCase(organisation);
//                                     System.out.println("(((((((((((((((( orgIds ))))))))))))))))"+orgId);
                                    System.out.println("(((((((((((((((( orgObj ))))))))))))))))" + orgObj);


                                    if (orgObj != null) {
                                        List<UserDetails> ud = this.userDetailsRepo.findByOrganisationAndEmailId(orgObj, email);
                                        if (!ud.isEmpty() && ud.getFirst() != null) {
                                            fieldErrorList.add(new FieldError(headerRow.get(4), email,
                                                    ErrorConstant.USERALREADYEXISTWITHMAIL));
                                        }
                                        int totalUserCreated = this.userDetailsRepo
                                                .getAllUsersByOrgId(orgObj.getOrganisationId());
                                        List<UserSetupTemp> userListWithNoError = this.userSetupRepository
                                                .findByProcessIdAndErrorsIsNull(processId);

                                        if (!userListWithNoError.isEmpty()) {
                                            totalUserCreated += userListWithNoError.size();
                                        }
                                        userSetupTemp.setOrganisation(organisation);
                                    } else {
                                        fieldErrorList.add(new FieldError(headerRow.get(2), organisation,
                                                ErrorConstant.UNKNOWNORGANISATION));
                                    }

                                }

                                final String errors = fieldErrorList.isEmpty() ? null
                                        : new ObjectMapper().writeValueAsString(fieldErrorList);

                                userSetupTemp.setErrors(errors);
                                this.userSetupRepository.save(userSetupTemp);
                                userSetupTempList.add(userSetupTemp);
                            }
                        }
                    } else {
                        map.put("errorMessage", "No record found.");
                    }
                } catch (final Exception ex) {
                    ex.printStackTrace();
                    map.put("errorMessage", "unable.to.process.data");
                }

                // Save Respondent Details
                this.userSetupRepository.saveAll(userSetupTempList);
                workbook.close();
                // Return success response
                return processId;
            } else {
                map.put("errorMessage", "sheet.not.found");
            }
        } catch (final Exception ex) {
            ex.printStackTrace();
            map.put("errorMessage", "unable.to.process.file");
        }
        return 0;
    }

    @Override
    public Map<String, Object> readUserData(Map<String, Object> map, long processId) {
        try {
            final List<UserSetupTemp> userSetupTempList = this.getUserSetupTemp(processId);
            map.put("userSetupTempList", userSetupTempList);
            map.put("processId", processId);
            return map;
        } catch (final Exception ex) {
            return map;
        }
    }

    @Override
    public List<UserSetupTemp> getUserSetupTemp(long processId) {
        return this.userSetupRepository.findByProcessId(processId);
    }

    @Override
    public boolean deleteUserSetupTemp(final long processId) {
        final List<UserSetupTemp> userSetupTempList = this.getUserSetupTemp(processId);
        if (!userSetupTempList.isEmpty()) {
            this.userSetupRepository.deleteAll(userSetupTempList);
            return true;
        }
        return false;
    }

    @Override
    public Map<String, Object> saveUserData(Map<String, Object> map, long processId) {
        try {
            // final long processId = Long.parseLong(String.valueOf(id));
            boolean isSaved = this.saveUserSetupTemp(map, processId);
            if (isSaved) {
                this.deleteUserSetupTemp(processId);
            }
            map.put("isSaved", isSaved);

            return map;
        } catch (final Exception ex) {
            return map;
        }
    }

    @Override
    public boolean saveUserSetupTemp(Map<String, Object> map, long processId) {
        final List<UserSetupTemp> userSetupTempList = this.userSetupRepository
                .findByProcessIdAndErrorsIsNull(processId);
        // this.deleteRespondentDetailsTemp(processId);
        if (userSetupTempList.size() > 0) {
            try {
                for (UserSetupTemp userSetupTemp : userSetupTempList) {
                    saveUserDetails(userSetupTemp);
                }
                map.put("SaveUpdateResponse", "BulkSaveSuccess");
            } catch (final Exception ex) {
                map.put("SaveUpdateResponse", "BulkSaveFailure");
                map.put("SaveUpdateFailureReason", ex.getMessage());
            }
            return true;
        }
        map.put("SaveUpdateResponse", "BulkSaveFailure");
        map.put("SaveUpdateFailureReason", "No Record To Save");
        return false;
    }

    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<String> handleMismatchedInputException(MismatchedInputException ex) {
        // Log the error for further investigation
        ex.printStackTrace();

        // Construct an error response message
        String errorMessage = "Invalid input format: " + ex.getMessage();

        // Return a ResponseEntity with an appropriate HTTP status code and the error message
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    public Page<?> getUserSetupTempRecord(long processId, SearchModelDTO searchingModel) {

        Pageable pageable;
        final int limit = searchingModel.getLimit();
        final int offset = searchingModel.getOffset();
        String sortField = searchingModel.getSortingField();
        String sortDirection = searchingModel.getSortDirection();

        try (Session session = this.entityManager.unwrap(Session.class);) {


            if (sortField == null) {
                searchingModel.setSortingField("ust.modifiedDate");
            }
            if (sortDirection == null || sortDirection.isEmpty()) {
                searchingModel.setSortDirection("asc");
            }

            String hqlQuery = "select ust from "
                    + " UserSetupTemp ust where ust.processId =: processId  ";

            final Map<String, Object> params = new HashMap<>();
            params.put("processId", processId);
            final Map<String, Object> dataMap = PagingSortingUtils.fetchDataListWithUpdatedQueryStringInMap(params,
                    hqlQuery, searchingModel, session);
            final Map<String, Object> resultmap = PagingSortingUtils.addPaginationDataInDataMap(dataMap, params,
                    hqlQuery,
                    false, searchingModel, session, "ust");
            /**
             * Fetch list
             */
            final List<?> searchedLists = (List<?>) resultmap.get(ConstantUtils.DATA_LIST);
            final long totalRows = (long) resultmap.get(ConstantUtils.TOTAL_ROWS);
            pageable = PageRequest.of(offset, limit);
            return new PageImpl<>(searchedLists, pageable, totalRows);

        } catch (Exception ex) {
            ex.printStackTrace();
            // You can log the exception here for further investigation

            // Return an error page or message if an exception occurs
            // For example:
            throw new RuntimeException("Failed to fetch user setup temp records.", ex);
        }

    }

    public void saveUserDetails(UserSetupTemp userSetupTemp) {
        System.out.println("In save user details method");
        final String status = userSetupTemp.getStatus();
        final String userName = userSetupTemp.getUserName();
        final String countryName = userSetupTemp.getCountry();
        final String emailId = userSetupTemp.getEmailId();
        final String contactNumber = userSetupTemp.getContactNo();
        final String organizationName = userSetupTemp.getOrganisation();
        final String userType = userSetupTemp.getUserType();
        final String address = userSetupTemp.getAddress();
        OrganisationMaster om = null;
        System.out.println("User details organisation name is : " + organizationName);
        om = this.organisationRepository.findByOrganisationNameIgnoreCase(organizationName);
        System.out.println("In save user details method om value is : " + om);
        final UserDetails ud = new UserDetails();
        final String organizationId = om.getOrganisationId();
        Long orgid = om.getId();
        System.out.println("In save user details method organisationid value is : " + organizationId);
        if (organizationId != null) {
            OrganisationUserNum oun = this.organisationUserNumRepository.findByOrgKeywordIdIgnoreCase(organizationId);
            long userCount = oun.getUserCount() + 1;
//            int totaluser=this.organisationUserNumRepository.get(String.valueOf(orgid));

            System.out.println("total user is : " + userCount);

            int totalUserCreated = this.userDetailsRepo.getAllUsersByOrgId(organizationId);
            System.out.println("totalUserCreated is : " + totalUserCreated);
            final Country c = this.countryRepository.findByCountryName(countryName);
            String keyword1 = om.getOrganisationId();
//                OrganisationUserNum oun1 = this.organisationUserNumRepository.findByOrgKeywordIdIgnoreCase(keyword1);
            System.out.println("Own keyword id is : " + oun);
            long maxCount = 0;
            System.out.println("max count before increment : " + maxCount);

            maxCount = oun.getUserCount();
            maxCount++;
            oun.setUserCount(maxCount);
            System.out.println("max count after increment : " + maxCount);

            organisationUserNumRepository.save(oun);
            String finalId = "";
            if (String.valueOf(maxCount).length() < 2) {
                finalId = keyword1 + "-00" + maxCount;
                System.out.println("final id in if : " + finalId);
            } else if (String.valueOf(maxCount).length() < 3) {
                finalId = keyword1 + "-0" + maxCount;
                System.out.println("final id in elseif : " + finalId);
            } else {
                finalId = keyword1 + "-" + maxCount;
                System.out.println("final id in else : " + finalId);
            }
            System.out.println("final id in outside ifelse block : " + finalId);
            ud.setUserKeywordId(finalId);
            ud.setUserName(userName);
            ud.setUserType(userType);
            ud.setContactNo(contactNumber);
            ud.setCountry(c);
            ud.setEmailId(emailId);
            ud.setAddress(address);
            if (status.equalsIgnoreCase("ACTIVE")) {
                ud.setStatus("ACTIVE");
            } else {
                ud.setStatus("INACTIVE");
            }

            if (organizationId != null) {
                ud.setOrganisation(om);
            }
            this.userDetailsRepo.save(ud);
        }

        // creating record in user login table

        // create autogenerated password
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(ch -> (char) ch)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        String password = pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        userDetailsServiceImpl.sendMail(ud.getUserKeywordId(), password, ud);
        final User u = new User();
        u.setUserId(ud.getId());
        u.setName(ud.getUserKeywordId());
        u.setRoles(userType);
        u.setPassword(this.passwordEncoder.encode(password));
        u.setEnabled(true);
        this.userLoginRepo.save(u);

        final PermissionGroup permissionGroup = this.groupRepository.findByName(userType);

        final UserPermissionGroup userPermissionGroup = new UserPermissionGroup();
        userPermissionGroup.setUserDetails(ud);

        if (organizationId != null && om != null) {
            userPermissionGroup.setOrganisation(om);
        }
        userPermissionGroup.setPermissionGroup(permissionGroup);
        this.userPermissisonGroupRepository.save(userPermissionGroup);
    }
}

