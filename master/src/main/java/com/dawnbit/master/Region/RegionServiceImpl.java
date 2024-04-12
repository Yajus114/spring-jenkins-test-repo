package com.dawnbit.master.Region;

import com.dawnbit.entity.master.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {
    @Autowired
    private RegionRepository regionRepository;

    @Override
    public List<Region> getAllRegions() {
        return this.regionRepository.getAllRegions();
    }


}
