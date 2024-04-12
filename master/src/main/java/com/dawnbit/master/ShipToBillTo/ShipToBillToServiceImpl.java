package com.dawnbit.master.ShipToBillTo;

import com.dawnbit.entity.master.ShipToBillTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ShipToBillToServiceImpl implements ShipToBillToService {
    @Autowired
    private ShipToBillToRepo shipToBillToRepository;

    @Override
    public List<ShipToBillTo> getShipToBillToBySiteId(Long siteId) {
        List<ShipToBillTo> shipToBillToList;
        shipToBillToList = shipToBillToRepository.findBySiteId(siteId);
        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFF");
        return shipToBillToList;
    }

    @Override
    public void deleteSiteAddress(Long id) {
        shipToBillToRepository.deleteById(id);
    }
}
