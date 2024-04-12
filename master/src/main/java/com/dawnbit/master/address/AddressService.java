package com.dawnbit.master.address;

import com.dawnbit.common.exception.CustomException;
import com.dawnbit.entity.master.Address;
import com.dawnbit.master.externalDTO.AddressDTO;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AddressService {

    void deleteAddress(Long id, String addCode) throws CustomException;

    List<Address> getAllAddresses();

    Optional<Address> getAddressById(Long id);

    Page<Address> getPaginatedAddresses(Pageable pageable);

    Object saveOrUpdateAddress(AddressDTO addressDTO);

    Page<?> fetchAddressData(SearchModelDTO searchModel, String organisationId);

    List<Address> fetchAddressOfOrg(String orgId);

    boolean checkAddressDuplicacy(String addressCode, long orgId);

    Address getAddressByCode(String code, long organiationId);


}


