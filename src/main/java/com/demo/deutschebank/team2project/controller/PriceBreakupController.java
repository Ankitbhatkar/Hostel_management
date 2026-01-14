package com.demo.deutschebank.team2project.controller;

import com.demo.deutschebank.team2project.service.PriceBreakupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceBreakupController {
    @Autowired
    PriceBreakupService priceBreakupService;

    @GetMapping("beds/{bedId}/price-breakup")
    public ResponseEntity<?> getPriceBreakup(@PathVariable int bedId,@RequestParam int durationInMonth) {
        return new ResponseEntity<>(priceBreakupService.getPriceBreakup(bedId,durationInMonth), HttpStatus.OK);
    }
}
