package com.dawnbit.master.country;

import com.dawnbit.entity.master.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<Country> fetchCountry() {
        return this.countryRepository.fetchCountry();
    }

    @Override
    public String fetchCountryCode(final String countryId) {
        return this.countryRepository.fetchCountryCode(countryId);
    }

    @Override
    public List<Country> fetchCountryOfRegion(String regionId) {
        return this.countryRepository.fetchCountryOfRegion(regionId);

    }

}