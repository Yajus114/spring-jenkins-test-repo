package com.dawnbit.master.Region;


import com.dawnbit.entity.master.Region;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegionService {


    List<Region> getAllRegions();
}
