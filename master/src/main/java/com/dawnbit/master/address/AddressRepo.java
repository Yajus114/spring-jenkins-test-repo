package com.dawnbit.master.address;

import com.dawnbit.entity.master.Address;
import com.dawnbit.entity.master.OrganisationMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address, Long> {
    @Query(value = "select * from address where org =?1", nativeQuery = true)
    List<Address> fetchAddressOfOrg(String organisationId);

    boolean existsByOrgAndAddressCode(OrganisationMaster org, String addressCode);

    @Query(value = "select * from address where address_code =?1 AND org = ?2", nativeQuery = true)
    Address findByAddressCode(String code, Long organisationId);

    //    @Query("SELECT a FROM Address a WHERE a.id = :addressId AND a.organizationId = :organizationId")
//    Optional<Address> findByIdAndOrganizationId(@Param("addressId") Long addressId, @Param("organizationId") Long organizationId);
//    @Query(value = "SELECT * FROM address WHERE id = ?1 AND org = ?2", nativeQuery = true)
//    Optional<Address> findByIdAndOrganisationId (Long id,Long organisationId);
}
