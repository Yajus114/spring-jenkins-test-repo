package com.dawnbit.master.address;

import com.dawnbit.common.exception.CustomException;
import com.dawnbit.common.utils.PagingSortingUtils;
import com.dawnbit.entity.master.Address;
import com.dawnbit.entity.master.Country;
import com.dawnbit.entity.master.OrganisationMaster;
import com.dawnbit.entity.master.ShipToBillTo;
import com.dawnbit.master.OrganisationMaster.OrganisationRepository;
import com.dawnbit.master.Region.RegionRepository;
import com.dawnbit.master.ShipToBillTo.ShipToBillToRepo;
import com.dawnbit.master.country.CountryRepository;
import com.dawnbit.master.externalDTO.AddressDTO;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.dawnbit.master.person.PersonServiceImpl.getObjects;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    RegionRepository regionRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    OrganisationRepository organisationRepository;
    @Autowired
    ShipToBillToRepo shiptoBilltoRepository;
    @Autowired
    private AddressRepo addressRepo;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void deleteAddress(Long id, String addCode) throws CustomException {
        // Check if the address is associated with any records in the shiptobillto table
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + addCode);
        boolean isAddressAssociated = isAddressAssociatedWithShiptoBillto(addCode);

        if (isAddressAssociated) {
            // Throw custom exception indicating that the address is associated with shiptobillto table
            throw new CustomException("Cannot delete address because it is associated with shiptobillto table");
        }

        // If address is not associated with shiptobillto table, proceed with deletion
        addressRepo.deleteById(id);
    }

    private boolean isAddressAssociatedWithShiptoBillto(String addCode) {
        // Implement the logic to check association with shiptobillto table
        List<ShipToBillTo> shiptoBillto = shiptoBilltoRepository.findByBilltoAndShipto(addCode);
        if (shiptoBillto != null && shiptoBillto.size() > 0) {
            return true;
        }
        return false;

    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepo.findAll();
    }

    @Override
    public Optional<Address> getAddressById(Long id) {
        return addressRepo.findById(id);
    }

    @Override
    public Page<Address> getPaginatedAddresses(Pageable pageable) {
        return addressRepo.findAll(pageable);
    }

    @Override
    public Object saveOrUpdateAddress(AddressDTO addressDTO) {
        long id = addressDTO.getId();
        Address address;
        if (id != 0) {
            Optional<Address> addressOptional = addressRepo.findById(id);
            if (addressOptional.isPresent()) {
                address = addressOptional.get();
            } else {
                throw new RuntimeException("Address not found.");
            }
        } else {
            address = new Address();
        }
        ;
        setCountryOrganisation(address, addressDTO);
        address.setAddress1(addressDTO.getAddress1().trim());
        address.setAddress2(addressDTO.getAddress2().trim());
        BeanUtils.copyProperties(addressDTO, address, "address1", "address2");
        return addressRepo.save(address);
    }

    private void setCountryOrganisation(Address address, AddressDTO addressDTO) {
        Country country = null;
        OrganisationMaster organisation = null;
        String countryId = addressDTO.getCountry();
        String org = addressDTO.getOrg();

        if (countryId != null) {
            country = countryRepository.findById(Long.valueOf(countryId)).orElseThrow(() -> new RuntimeException("Country not found"));
        }
        if (org != null) {
            organisation = organisationRepository.findById(Long.parseLong(org)).orElseThrow(() -> new RuntimeException("Organisation not found"));
        }
        address.setCountry(country);
        address.setOrg(organisation);
    }

    @Override
    public Page<?> fetchAddressData(SearchModelDTO searchModel, String organisationId) {
        final int limit = searchModel.getLimit();
        final int offset = searchModel.getOffset();
        final String sortField = searchModel.getSortingField();
        final String sortDirection = searchModel.getSortDirection();

        try (Session session = entityManager.unwrap(Session.class)) {
            if (sortField == null) {
                searchModel.setSortingField("address.id");
            }
            if (sortDirection == null || sortDirection.isEmpty()) {
                searchModel.setSortDirection("desc");
            }
            final Map<String, Object> params = new HashMap<>();

            String hqlQuery = "select address " + "FROM Address address where address.id is not null";

            if (organisationId != null && !organisationId.isEmpty()) {
                hqlQuery += " AND address.org.id = :organisationId";
                params.put("organisationId", organisationId);
            }

            final Map<String, Object> dataMap = PagingSortingUtils.fetchDataListWithUpdatedQueryStringInMaps(params, hqlQuery, searchModel, session);
            final Map<String, Object> resultmap = PagingSortingUtils.addPaginationDataInDataMap(dataMap, params, searchModel, session, "address");


            return getObjects(limit, offset, resultmap);
        }
    }

    @Override
    public List<Address> fetchAddressOfOrg(String orgId) {
        return addressRepo.fetchAddressOfOrg(orgId);
    }

    @Override
    public boolean checkAddressDuplicacy(String addressCode, long orgId) {
        return addressRepo.existsByOrgAndAddressCode(organisationRepository.findById(orgId).orElseThrow(() -> new RuntimeException("Organisation not found, orgId: " + orgId)), addressCode);
    }

    @Override
    public Address getAddressByCode(String code, long organisationId) {
        return addressRepo.findByAddressCode(code, organisationId);
    }


}
