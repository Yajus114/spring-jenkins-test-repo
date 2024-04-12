package com.dawnbit.master.ShipToBillTo;

import com.dawnbit.entity.master.ShipToBillTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/ShipToBillTo")
public class ShipToBillToController {
    @Autowired
    private ShipToBillToService shipToBillToService;

    @GetMapping("/shiptobillto/{siteId}")
    public ResponseEntity<List<ShipToBillTo>> getShipToBillToBySiteId(@PathVariable Long siteId) {
        List<ShipToBillTo> shipToBillToList = shipToBillToService.getShipToBillToBySiteId(siteId);

        return ResponseEntity.ok(shipToBillToList);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSiteAddress(@PathVariable Long id) {
        shipToBillToService.deleteSiteAddress(id);
    }


}
