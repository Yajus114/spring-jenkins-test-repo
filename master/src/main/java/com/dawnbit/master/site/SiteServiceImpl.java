package com.dawnbit.master.site;

import com.dawnbit.common.utils.PagingSortingUtils;
import com.dawnbit.entity.master.*;
import com.dawnbit.master.Employee.EmployeeRepository;
import com.dawnbit.master.OrganisationMaster.DuplicateOrganisationException;
import com.dawnbit.master.OrganisationMaster.OrganisationRepository;
import com.dawnbit.master.ShipToBillTo.ShipToBillToRepo;
import com.dawnbit.master.commonNum.OrganisationUserNumRepository;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import com.dawnbit.master.externalDTO.SiteAddressDto;
import com.dawnbit.master.externalDTO.SiteDTO;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.dawnbit.master.person.PersonServiceImpl.getObjects;

@Service
@Slf4j
public class SiteServiceImpl implements SiteService {

    @Autowired
    private SiteRepo siteRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private OrganisationRepository orgRepo;
    @Autowired
    private OrganisationUserNumRepository organisationUserNumRepository;

    @Autowired
    private ShipToBillToRepo shipToBillToRepo;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserDetailsRepo userDetailsRepo;

    @Autowired
    private UserLoginRepo userRepository;

    @Override
    public Site saveOrUpdateSite(SiteDTO siteDTO) {
        String siteName = siteDTO.getSiteName().trim();
        OrganisationMaster org = orgRepo.findById(Long.parseLong(siteDTO.getOrgId())).orElseThrow(() -> new RuntimeException("Organisation not found"));
        long id = siteDTO.getId();
        if (siteRepo.existsBySiteNameIgnoreCaseAndOrgAndIdNot(siteName, org, id))
            throw new DuplicateOrganisationException("Site with the same name already exists");

        Site site;
        if (id != 0)
            site = siteRepo.findById(id).orElseThrow(() -> new RuntimeException("Site not found"));
        else
            site = new Site();
        BeanUtils.copyProperties(siteDTO, site);
        setOrganisationAddressPerson(site, siteDTO, org);

        // Save or update Site entity
        site.setStatus(siteDTO.getStatus().equalsIgnoreCase("true") ? "ACTIVE" : "INACTIVE");
        OrganisationUserNum oun = this.organisationUserNumRepository.findByOrgKeywordIdIgnoreCase(site.getOrg().getOrganisationId());
        oun.setSiteCount(oun.getSiteCount() + 1);
        this.organisationUserNumRepository.save(oun);
        Site savedSite = siteRepo.save(site);

        // Save or update ShipToBillTo details
        saveOrUpdateShipToBillTo(savedSite, siteDTO.getSiteAddress());

        return savedSite;
    }


    private void saveOrUpdateShipToBillTo(Site site, List<SiteAddressDto> siteAddressList) {
        if (siteAddressList != null && !siteAddressList.isEmpty()) {
            for (SiteAddressDto siteAddress : siteAddressList) {
                // Check if billTo and shipTo have the same address code
//                boolean sameAddressCode = siteAddress.getBillTo().equals(siteAddress.getShipTo());

                // Create ShipToBillTo object
                ShipToBillTo shipToBillTo = new ShipToBillTo();
                shipToBillTo.setOrg(site.getOrg());
                shipToBillTo.setSite(site);
                shipToBillTo.setBillTo(siteAddress.getBillTo());
                shipToBillTo.setShipTo(siteAddress.getShipTo());
                shipToBillTo.setBillToContact(siteAddress.getBillToContact());
                shipToBillTo.setShipToContact(siteAddress.getShipToContact());

                // Save one record if billTo and shipTo have the same address code, otherwise save two records
//                if (sameAddressCode) {
                shipToBillToRepo.save(shipToBillTo);
//                } else {
//                    // Create a new ShipToBillTo object for billTo
//                    ShipToBillTo billToShipToBillTo = new ShipToBillTo();
//                    billToShipToBillTo.setOrg(site.getOrg());
//                    billToShipToBillTo.setSite(site);
//                    billToShipToBillTo.setBillTo(siteAddress.getBillTo());
//                    billToShipToBillTo.setBillToContact(siteAddress.getBillToContact());
//                    billToShipToBillTo.setShipTo(siteAddress.getShipTo());
//                    billToShipToBillTo.setShipToContact(siteAddress.getShipToContact());
//                    shipToBillToRepo.save(billToShipToBillTo);
//
//                    // Save the shipTo ShipToBillTo object
//                    shipToBillToRepo.save(shipToBillTo);
//                }
            }
        }
    }


    /**
     * Author: Bharti
     * Method to set address and organisationin person
     *
     * @param site
     * @param siteDTO
     * @param org
     */
//    private void setOrganisationAddressPerson(Site site, SiteDTO siteDTO, OrganisationMaster org) {
//        Person person;
//        site.setOrg(org);
//        if (siteDTO.getPerson() != null && !siteDTO.getPerson ().isEmpty ()) {
//            person = new Person();
//            person.setId(Long.parseLong(siteDTO.getPerson()));
//            site.setPerson(person);
//        }
//    }
    private void setOrganisationAddressPerson(Site site, SiteDTO siteDTO, OrganisationMaster org) {
        site.setOrg(org);

        if (siteDTO.getPerson() != null && !siteDTO.getPerson().isEmpty()) {
            // Assuming you have access to a service or repository to retrieve the Employee object by ID
            long employeeId = Long.parseLong(siteDTO.getPerson());
            Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

            // Check if the optional contains a value
            if (optionalEmployee.isPresent()) {
                // Get the actual Employee object from the Optional
                Employee employee = optionalEmployee.get();
                // Set the Employee object to the site
                site.setPerson(employee);
            } else {
                // Handle case where the employee with the given ID is not found
                // You can log an error or throw an exception based on your requirement
                log.error("Employee with ID {} not found", employeeId);
                throw new RuntimeException("Employee with ID " + employeeId + " not found");
            }
        }
    }


//    private void setDataInCommonNum() {
//        CommonNum cn = commonNumRepository.findByName("SITE_MASTER");
//        long siteCount = cn.getCountRows() + 1;
//        cn.setCountRows(siteCount);
//        commonNumRepository.save(cn);
//        SiteUserNum sun = new SiteUserNum();
////        sun.setSiteKeywordId(site.getSiteId());
//        sun.setUserCount(0);
//        siteUserNumRepository.save(sun);
//    }

//    private String generateSideId() {
//        String siteId;
//        CommonNum cn = this.commonNumRepository.findByName("SITE_MASTER");
//        long siteCount = cn.getCountRows() + 1;
//        if (siteCount < 10) {
//            siteId = "STE-00" + siteCount;
//        } else if (siteCount < 100) {
//            siteId = "STE-0" + siteCount;
//        } else {
//            siteId = "STE-" + siteCount;
//        }
//        return siteId;
//    }

    @Override
    public void deleteSite(Long id) {
        siteRepo.deleteById(id);
    }

    @Override
    public List<Site> getAllSites() {
        return siteRepo.findAll();
    }

    @Override
    public Optional<Site> getSiteById(Long id) {
        return siteRepo.findById(id);
    }

    @Override
    public boolean checkSiteNameDuplicacy(String siteName, long orgId) {
        return siteRepo.existsBySiteNameAndOrg(siteName, orgRepo.findById(orgId).orElseThrow(() -> new RuntimeException("Organisation not found, orgId: " + orgId)));
    }

    @Override
    public Page<?> fetchSiteData(SearchModelDTO searchModel, String organisationId) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("-------------------username is : --------------"+userName);
        User user = this.userRepository.findByName(userName).orElse(null);

        final UserDetails userDetail = this.userDetailsRepo.findByIds(user.getUserId());



        final int limit = searchModel.getLimit();
        final int offset = searchModel.getOffset();
        final String sortField = searchModel.getSortingField();
        final String sortDirection = searchModel.getSortDirection();


        try (Session session = this.entityManager.unwrap(Session.class)) {
            if (sortField == null) {
                searchModel.setSortingField("site.id");
            }
            if (sortDirection == null || sortDirection.isEmpty()) {
                searchModel.setSortDirection("desc");
            }
            final Map<String, Object> params = new HashMap<>();

            String hqlQuery = "select site "
                    + "FROM Site site where site.siteName is not null ";

            // Add organization ID filter
//            if (organisationId != null && !organisationId.isEmpty()) {
//                hqlQuery += " AND site.org.id = :organisationId";
//                params.put("organisationId", organisationId);
//            }
            if (userDetail.getSite() != null) {
                System.out.println ("userDetail.getSite() : " + userDetail.getSite());
                // If user has a site ID, filter by that site ID
                hqlQuery += " AND site.id = :site";
                params.put("site", userDetail.getSite().getId ());
            } else {
                // If no site ID associated, filter by organization ID
                if (organisationId != null && !organisationId.isEmpty()) {
                    hqlQuery += " AND site.org.id = :organisationId";
                    params.put("organisationId", organisationId);
                }
            }

            final Map<String, Object> dataMap = PagingSortingUtils.fetchDataListWithUpdatedQueryStringInMaps(params,
                    hqlQuery, searchModel, session);
            final Map<String, Object> resultmap = PagingSortingUtils.addPaginationDataInDataMap(dataMap, params,
                    searchModel, session, "site");
            return getObjects(limit, offset, resultmap);
        }
    }


    @Override
    public Page<Site> getPaginatedSites(Pageable pageable) {
        return siteRepo.findAll(pageable);
    }

    @Override
    public Object fetchSiteByOrg(String orgId) {
        return siteRepo.fetchSiteByOrg(orgId);
    }

}