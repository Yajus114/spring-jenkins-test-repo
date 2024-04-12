package com.dawnbit.master.address;

import com.dawnbit.common.exception.CustomException;
import com.dawnbit.entity.master.Address;
import com.dawnbit.master.externalDTO.AddressDTO;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/address")
@Slf4j
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> saveOrUpdateAddress(@RequestBody AddressDTO addressDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("addressList", addressService.saveOrUpdateAddress(addressDTO));
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/delete")
    public void deleteAddress(@RequestParam Long id, @RequestParam String addCode) throws CustomException, UnsupportedEncodingException {
        addressService.deleteAddress(id, URLDecoder.decode(addCode, StandardCharsets.UTF_8.toString()));
    }


    @GetMapping("/all")
    public ResponseEntity<List<Address>> getAllAddresses() {
        try {
            List<Address> addresses = addressService.getAllAddresses();
            return ResponseEntity.ok(addresses);
        } catch (RuntimeException e) {
//            log.error("Error in fetching all addresses", e);
            return ResponseEntity.internalServerError().body(List.of());
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<Address> getAddressById(@RequestParam Long id) {
        Optional<Address> address = addressService.getAddressById(id);
        return address.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getByCode")
    public ResponseEntity<Address> getAddressByCode(@RequestParam String code, @RequestParam Long organisationId) {
        Address address = addressService.getAddressByCode(code, organisationId);
        if (address != null) {
            return ResponseEntity.ok(address);
        } else {
            // Handle the case where no address is found with the given code
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/paginated")
    public Page<Address> getPaginatedAddress(Pageable pageable) {
        return addressService.getPaginatedAddresses(pageable);
    }

    @PostMapping("/fetchAddressData/{organisationId}")
    public ResponseEntity<Map<String, Object>> fetchAddressData(@RequestBody SearchModelDTO searchModel, @PathVariable String organisationId) {
        Map<String, Object> map = new HashMap<>();
        map.put("address", addressService.fetchAddressData(searchModel, organisationId));
        return ResponseEntity.ok(map);
    }

    //    @GetMapping("/fetchAddressOfOrg/{organisationId}")
//    public ResponseEntity<Map<String, Object>> fetchAddressOfOrg(@PathVariable String organisationId) {
//        final Map<String, Object> map = new HashMap<>();
//        map.put("address", addressService.fetchAddressOfOrg(organisationId));
//        return ResponseEntity.ok(map);
//    }
    @GetMapping("/fetchAddressOfOrg/{organisationId}")
    public ResponseEntity<List<Address>> fetchAddressOfOrg(@PathVariable String organisationId) {
        List<Address> addresses = addressService.fetchAddressOfOrg(organisationId);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/checkAddressDuplicacy")
    public ResponseEntity<Map<String, String>> checkAddressDuplicacy(@RequestParam String addressCode, @RequestParam long orgId) {
        Map<String, String> map = new HashMap<>();
        if (addressService.checkAddressDuplicacy(addressCode, orgId)) {
            map.put("address", "duplicateAddress");
        } else {
            map.put("address", "validAddress");
        }
        return ResponseEntity.ok(map);
    }
}
