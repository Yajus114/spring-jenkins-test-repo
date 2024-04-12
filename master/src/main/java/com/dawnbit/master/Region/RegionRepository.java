package com.dawnbit.master.Region;

import com.dawnbit.entity.master.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    @Query(value = " select * from region", nativeQuery = true)
    List<Region> getAllRegions();

//    Object fetchRegionCode(String regionId);

}
