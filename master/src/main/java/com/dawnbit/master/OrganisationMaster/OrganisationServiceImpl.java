package com.dawnbit.master.OrganisationMaster;

import com.dawnbit.common.utils.PagingSortingUtils;
import com.dawnbit.entity.master.*;
import com.dawnbit.entity.master.Currency;
import com.dawnbit.master.Region.RegionRepository;
import com.dawnbit.master.commonNum.CommonNumRepository;
import com.dawnbit.master.commonNum.OrganisationUserNumRepository;
import com.dawnbit.master.country.CountryRepository;
import com.dawnbit.master.currency.CurrencyRepository;
import com.dawnbit.master.externalDTO.OrgListDTO;
import com.dawnbit.master.externalDTO.OrganisationDropDownDTO;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import com.dawnbit.master.site.SiteRepo;
import com.dawnbit.master.userdetails.UserDetailsRepo;
import com.dawnbit.master.userlogin.UserLoginRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.dawnbit.master.person.PersonServiceImpl.getObjects;

@Slf4j
@Service
public class OrganisationServiceImpl implements OrganisationService {
    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private CommonNumRepository commonNumRepository;

    @Autowired
    private OrganisationUserNumRepository organisationUserNumRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private  UserLoginRepo userRepository;

    @Autowired
    private  UserDetailsRepo userDetailsRepo;


    @Autowired
    private SiteRepo siteRepo;


    /**
     * Save or update an organization based on the provided DTO.
     *
     * @param  orgListDTO  DTO containing organization information
     * @return             the saved or updated OrganisationMaster object
     * @throws DuplicateOrganisationException if the organization name already exists
     * @throws RuntimeException                if the organization could not be saved
     * @author DB-CPU018
     */
    @Override
    public OrganisationMaster saveOrUpdateOrganisation(OrgListDTO orgListDTO) {
        String organisationName = orgListDTO.getOrganisationName();
        long id = orgListDTO.getId();

        // Check duplicate for organization name
        log.info("organisation name" + organisationName + "  and id " + id);
        if (organisationRepository.existsByOrganisationNameIgnoreCaseAndIdNot(organisationName, id)) {
            throw new DuplicateOrganisationException("Organization with the same name already exists");
        }

        OrganisationMaster organisation;
        if (id != 0) {
            // If ID is present and not equal to 0, check if the organization exists for updating
            organisation = organisationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Organization not found"));

            setCurrency(organisation, orgListDTO.getCurrency ());
            // Update only specific fields with the values from the DTO
            updateOrganisationFields(organisation, orgListDTO);
            organisation.setStatus(orgListDTO.getStatus().equalsIgnoreCase("true") ? "ACTIVE" : "INACTIVE");

            // Save the updated organization
            organisation = organisationRepository.save(organisation);
        } else {
            // If ID is 0, it's a new organization, create and save with a new ID
            organisation = new OrganisationMaster();
            // Set all fields with the values from the DTO

            BeanUtils.copyProperties(orgListDTO, organisation);

            String orgId = this.generateOrgId();

            organisation.setOrganisationId(orgId);
            setCurrency(organisation, orgListDTO.getCurrency ());

            // Save the new organisation
            organisation.setStatus(orgListDTO.getStatus().equalsIgnoreCase("true") ? "ACTIVE" : "INACTIVE");

            organisation = organisationRepository.save(organisation);
            this.setDataInCommonNum(organisation, "ORGANISATION_MASTER");
        }

        return organisation;
    }

    /**
     * Set the currency for the given organisation.
     *
     * @param  organisation  the organisation for which the currency is being set
     * @param  currencyId    the ID of the currency to be set
     *                         (if null, the currency will be set to null)
     * @author DB-CPU018
     */
    private void setCurrency(OrganisationMaster organisation, Long currencyId) {
        // Fetch the Region and Country entities from the database
        Currency currency =null;
        if (currencyId != null) {
            currency = currencyRepository.findById(currencyId)
                    .orElseThrow(() -> new RuntimeException("Currency not found"));

        }
        organisation.setCurrency (currency);
    }

    /**
     * Generates a unique organization ID based on the count of existing organizations.
     *
     * @return         the generated organisation ID
     * @author DB-CPU018
     */
    public String generateOrgId() {
        String orgId;
        CommonNum cn = this.commonNumRepository.findByName("ORGANISATION_MASTER");
        long orgCount = cn.getCountRows() + 1;
        if (orgCount < 10) {
            orgId = "ORG-00" + orgCount;
        } else if (orgCount < 100) {
            orgId = "ORG-0" + orgCount;
        } else {
            orgId = "ORG-" + orgCount;
        }

        return orgId;
    }

    /**
     * Sets the data in the CommonNum and OrganisationUserNum entities for the given OrganisationMaster and name.
     *
     * @param  org  the OrganisationMaster object
     * @param  name the name of the CommonNum entity
     *                 (e.g. "ORGANISATION_MASTER", "ORGANISATION_USER")
     * @author DB-CPU018
     */
    public void setDataInCommonNum(OrganisationMaster org, String name) {
        CommonNum cn = this.commonNumRepository.findByName(name);
        cn.setCountRows(cn.getCountRows() + 1);
        this.commonNumRepository.save(cn);

        OrganisationUserNum orgUserNum = new OrganisationUserNum();
        orgUserNum.setOrgKeywordId(org.getOrganisationId());
        orgUserNum.setUserCount(0);
        orgUserNum = this.organisationUserNumRepository.save(orgUserNum);
    }

    /**
     * Checks for duplicate organisation name and throws an exception if found.
     *
     * @param  organisationName    the name of the organisation to check for duplicates
     * @param  idToExclude         the ID to exclude from the duplicate check
     *                              (if 0, no ID is excluded)
     * @throws DuplicateOrganisationException if a duplicate organisation is found
     * @author DB-CPU018
     */
    private void checkDuplicateName(String organisationName, long idToExclude) {
        int count = organisationRepository.countByOrganisationNameIgnoreCase(organisationName);
        if (count > 0) {
            throw new DuplicateOrganisationException("Organization with the same name already exists");
        }
    }

    /**
     * Updates specific fields of the given OrganisationMaster object with the values from the OrgListDTO object.
     *
     * @param  organisation   the OrganisationMaster object to be updated
     * @param  orgListDTO    the OrgListDTO object containing the new
     *                         values for the OrganisationMaster object
     * @author DB-CPU018
     */
    private void updateOrganisationFields(OrganisationMaster organisation, OrgListDTO orgListDTO) {
        // Update only specific fields with the values from the DTO
        BeanUtils.copyProperties(orgListDTO, organisation, "id", "keyWord");

        // If there are additional fields or custom logic needed, you can handle them here
    }

    /**
     * Deletes an organisation by its ID.
     *
     * @param  id  the ID of the organisation to be deleted
     *             (if 0, no organisation is deleted)
     * @author DB-CPU018
     */
    @Override
    public void deleteOrganisation(Long id) {
        organisationRepository.deleteById(id);
    }

    /**
     * Retrieve all OrganisationMaster objects.
     *
     * @return         list of all OrganisationMaster objects
     */
    @Override
    public List<OrganisationMaster> getAllOrganisations() {
        return organisationRepository.findAll();
    }

    /**
     * Retrieves an organisation by its ID.
     *
     * @param  id   the ID of the organisation to retrieve
     * @return      an Optional containing the OrganisationMaster if found, empty otherwise
     */
    @Override
    public Optional<OrganisationMaster> getOrganisationById(Long id) {
        return organisationRepository.findById(id);
    }

//    @Override
//    public OrganisationMaster checkKeywordDuplicacy(String keyword) {
//
//        return this.organisationRepository.checkKeywordDuplicacy(keyword);
//
//    }


    /**
     * Checks for duplicacy of organisation name.
     *
     * @param  keyword         the keyword to check for duplicacy
     * @param  organisationId   the ID of the organisation
     * @return                 the OrganisationMaster object containing the checked organisation name
     */
    @Override
    public OrganisationMaster checkOrganisationNameDuplicacy(String keyword, String organisationId) {
        OrganisationMaster om;
        om = this.organisationRepository.checkOrganisationNameDuplicacy(keyword, 0);

        return om;
    }

    @Override
    public Page<OrganisationMaster> getPaginatedOrganisations(Pageable pageable) {
        return organisationRepository.findAll(pageable);
    }

    /**
     * A method to fetch organization data based on search criteria.
     *
     * @param  searchModel	the search criteria for fetching organization data
     * @return         	the page of organization data based on the search criteria
     * @author DB-CPU018
     */

    @Override
    public Page<?> fetchOrganisationData(SearchModelDTO searchModel) {

        final int limit = searchModel.getLimit();
        final int offset = searchModel.getOffset();
        final String sortField = searchModel.getSortingField();
        final String sortDirection = searchModel.getSortDirection();
        List<String> organizationIds= this.getOrganizationIdsByUserRole();
        try (Session session = this.entityManager.unwrap(Session.class)) {
            if (sortField == null) {
                searchModel.setSortingField("org.id");
            }
            if (sortDirection == null || sortDirection.isEmpty()) {
                searchModel.setSortDirection("desc");
            }
            final Map<String, Object> params = new HashMap<>();

            String hqlQuery = "select new com.dawnbit.master.externalDTO.SearchModelDTO(org) "
                    + "FROM OrganisationMaster org where org.organisationName is not null and org.id in(:organizationIds)";
//
            params.put("organizationIds",organizationIds);
            final Map<String, Object> dataMap = PagingSortingUtils.fetchDataListWithUpdatedQueryStringInMaps(params,
                    hqlQuery, searchModel, session);
            final Map<String, Object> resultmap = PagingSortingUtils.addPaginationDataInDataMap(dataMap, params,
                    searchModel, session, "org");


            /**
             * Fetch list
             */
            return getObjects(limit, offset, resultmap);
        }
    }

    /**
     * A method to retrieve organization IDs based on the user's role.
     *
     * @return         	list of organization IDs
     */
    public  List<String> getOrganizationIdsByUserRole(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findByName(userName).orElse(null);
        final UserDetails userDetail = this.userDetailsRepo.findByIds(user.getUserId());
        System.out.println("uer details are : -------------------------"+userDetail.getUserType());
        long organizationId=userDetail.getOrganisation().getId();
        List<String> organizationIds=new ArrayList<>();
        if (userDetail.getUserType().equalsIgnoreCase("GlobalAdmin")){
            organizationIds=this.organisationRepository.getAllOrganizationIds();
        }else if(userDetail.getUserType().equalsIgnoreCase("EMPLOYEE")){
            System.out.println("11111111111111111in else if condition"+userDetail.getUserType().equalsIgnoreCase("EMPLOYEE"));
            organizationIds.add(String.valueOf(organizationId));
//            organizationIds=this.organisationRepository.findByid(organizationId);
        }else{
            System.out.println("------------------In else condition");
            organizationIds.add(String.valueOf(organizationId));
        }

        return organizationIds;
    }

    @Override
    public List<OrganisationDropDownDTO> fetchOrganizations() {
        List<String> orgIdsList = this.getOrganizationIdsByUserRole();
        return this.organisationRepository.getOrganizationByIds(orgIdsList);
    }
    @Override
    public List<OrganisationMaster> getOrganisationListBasedOnRole() {
        List<String> orgIds = this.getOrganizationIdsByUserRole();

        return organisationRepository.getOrganisationListBasedOnRole(orgIds);
    }

    @Override
    public List<Map<String, Object>> getOrganisationUserCountForDashboard() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("-------------------username is : --------------" + userName);
        User user = this.userRepository.findByName(userName).orElse(null);
        final UserDetails userDetail = this.userDetailsRepo.findByIds(user.getUserId());
        final List<Map<String, Object>> mapList = new ArrayList<>();
        List<String> orgIds = this.getOrganizationIdsByUserRole();


        // Get counts for active and inactive sites
        int activeSiteCount = this.siteRepo.getActiveSiteCount();
        int inactiveSiteCount = this.siteRepo.getInactiveSiteCount();

        // Get total site count
        int totalSiteCount = activeSiteCount + inactiveSiteCount;

        for (String id : orgIds) {
            OrganisationMaster orgObject = this.organisationRepository.findById(Long.valueOf(id)).get();
            Map<String, Object> map = new HashMap<>();
            map.put("category", orgObject.getOrganisationName());
            map.put("userCount", this.userDetailsRepo.getUserCountByOrgId(orgObject.getId()));
            map.put("inactiveUserCount", this.userDetailsRepo.getInactiveUserCountByOrgId(orgObject.getId()));
//

            map.put("activeSiteCount", activeSiteCount);
            map.put("inactiveSiteCount", inactiveSiteCount);
            map.put("totalSiteCount", totalSiteCount);

            if (userDetail.getUserType().equalsIgnoreCase("GlobalAdmin")) {
                map.put("activeOrgCount", this.organisationRepository.getOrgCountForAdmin());
                map.put("inactiveOrgCount", this.organisationRepository.getInactiveOrgCountForAdmin());
                int activeOrgCount = this.organisationRepository.getOrgCountForAdmin();
                int inactiveOrgCount = this.organisationRepository.getInactiveOrgCountForAdmin();
                int totalOrgCount = activeOrgCount + inactiveOrgCount;
                map.put("TotalOrgCount", totalOrgCount);
                map.put("siteCount", this.siteRepo.getSiteCount());
            } else if (userDetail.getUserType().equalsIgnoreCase("OrganisationAdmin")) {
                map.put("activeOrgCount", this.organisationRepository.getOrgCount(orgObject.getId()));
                map.put("inactiveOrgCount", this.organisationRepository.getInactiveOrgCount(orgObject.getId()));
                int activeOrgCount =Integer.parseInt (this.organisationRepository.getOrgCount(orgObject.getId()));
                int inactiveOrgCount =Integer.parseInt (this.organisationRepository.getInactiveOrgCount(orgObject.getId()));
                int totalOrgCount = activeOrgCount + inactiveOrgCount;
                map.put("TotalOrgCount", totalOrgCount);
                // Adjust site count based on the user's site ID
                if (userDetail.getSite() != null) {
                    map.put("siteCount", this.siteRepo.getSiteCountBySiteId(userDetail.getSite().getId()));
                } else {
                    map.put("siteCount", this.siteRepo.getSiteCountByOrgId(orgObject.getId()));
                }
            } else {
                map.put("activeOrgCount", this.organisationRepository.getOrgCount(orgObject.getId()));
                map.put("inactiveOrgCount", this.organisationRepository.getInactiveOrgCount(orgObject.getId()));
                int activeOrgCount =Integer.parseInt (this.organisationRepository.getOrgCount(orgObject.getId()));
                int inactiveOrgCount =Integer.parseInt (this.organisationRepository.getInactiveOrgCount(orgObject.getId()));
                int totalOrgCount = activeOrgCount + inactiveOrgCount;
                map.put("TotalOrgCount", totalOrgCount);
                // Adjust site count based on the user's site ID
                if (userDetail.getSite() != null) {
                    map.put("siteCount", this.siteRepo.getSiteCountBySiteId(userDetail.getSite().getId()));
                } else {
                    map.put("siteCount", this.siteRepo.getSiteCountByOrgId(orgObject.getId()));
                }
            }

            mapList.add(map);
        }
        return mapList;
    }

    /**
     * Retrieves a list of active OrganisationMaster objects based on the user's role.
     *
     * @return         	a list of OrganisationMaster objects representing the active organisations
     *                 	based on the user's role
     */
    @Override
    public List<OrganisationMaster> getActiveOrganisationListBasedOnRole() {
        List<String> orgIds = this.getOrganizationIdsByUserRole();

        return organisationRepository.getActiveOrganisationListBasedOnRole(orgIds);
    }

}
