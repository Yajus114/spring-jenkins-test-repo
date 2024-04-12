package com.dawnbit.master.ShipToBillTo;

import com.dawnbit.entity.master.ShipToBillTo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShipToBillToService {
    List<ShipToBillTo> getShipToBillToBySiteId(Long siteId);

    void deleteSiteAddress(Long id);
}
