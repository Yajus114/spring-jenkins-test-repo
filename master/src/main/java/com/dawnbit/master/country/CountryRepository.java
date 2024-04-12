package com.dawnbit.master.country;

import com.dawnbit.entity.master.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query(value = " select * from country order by country_name", nativeQuery = true)
    List<Country> fetchCountry();

    @Query(value = " select country_code from country where country_id=?1 ", nativeQuery = true)
    String fetchCountryCode(String countryId);

//    @Query("select ct.countryName as countryName from Country ct order by ct.countryName asc ")
//    List<String> getAllCountryName();
//
//    @Query(value = "select * from country ct where  ct.country_name =?1 ", nativeQuery = true)
//    Country getCountryObj(String s);

    @Query(value = "select * from country where region_id =?1 order by country_name", nativeQuery = true)
    List<Country> fetchCountryOfRegion(String regionId);

    Country findByCountryName(String countryName);

//    @Query(value = "select ct.country_name from country ct where ct.region_id =?1 order by ct.country_name asc ", nativeQuery = true)
//    List<String> listOfCountryOfRegion(String regionId);
}