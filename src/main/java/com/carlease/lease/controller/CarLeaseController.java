package com.carlease.lease.controller;

import com.carlease.lease.leaseDto.LeaseRequest;
import com.carlease.lease.leaseDto.LeaseResponse;
import com.carlease.lease.services.CarLeaseServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CarLeaseController {

    @Autowired
    CarLeaseServices carLeaseServices;

    @PostMapping("monthlyleasecalculator/")
    public ResponseEntity<LeaseResponse> getFilteredLocationInformation(@RequestBody LeaseRequest leaseRequest) {
        log.info("Entered client endpoint to fetch customer details");
        return ResponseEntity.ok(carLeaseServices.calculateCarLease(leaseRequest));
    }
}
