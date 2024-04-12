package com.dawnbit.master.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/currency")
@RestController
public class CurrrencyController {
    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/fetchCurrency")
    public ResponseEntity<Map<String, Object>> fetchCurrency() {
        final Map<String, Object> map = new HashMap<>();

        map.put("currency", this.currencyService.fetchCurrency());
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}
