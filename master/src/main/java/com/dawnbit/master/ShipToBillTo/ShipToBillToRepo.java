package com.dawnbit.master.ShipToBillTo;

import com.dawnbit.entity.master.ShipToBillTo;
import com.dawnbit.entity.master.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipToBillToRepo extends JpaRepository<ShipToBillTo, Long> {

    ShipToBillTo findBySite(Site site);

    List<ShipToBillTo> findBySiteId(Long siteId);

    @Query("SELECT s FROM ShipToBillTo s WHERE s.billTo = ?1 OR s.shipTo = ?1")
    List<ShipToBillTo> findByBilltoAndShipto(String addCode);

    // You can add other query methods as needed

}
