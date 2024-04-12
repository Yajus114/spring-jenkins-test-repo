package com.dawnbit.master.country;

import com.dawnbit.entity.master.Country;

import java.util.List;

public interface CountryService {

    List<Country> fetchCountry();

    String fetchCountryCode(String countryId);

    List<Country> fetchCountryOfRegion(String regionId);
}
