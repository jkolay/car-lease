package com.carlease.lease.controller;

import com.carlease.lease.model.request.LeaseRequest;
import com.carlease.lease.model.response.LeaseResponse;
import com.carlease.lease.service.CarLeaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/car-lease")
public class CarLeaseController {

    @Autowired
    CarLeaseService carLeaseServices;

    @PostMapping("createLease")
    public ResponseEntity<LeaseResponse> createLease(@RequestBody LeaseRequest leaseRequest) throws Exception {
        log.info("Entered client endpoint to fetch customer details");
        return ResponseEntity.ok(carLeaseServices.createNewLease(leaseRequest));
    }

    @PostMapping("monthlyleasecalculator/")
    public ResponseEntity<LeaseResponse> getFilteredLocationInformation(@RequestBody LeaseRequest leaseRequest) {
        log.info("Entered client endpoint to fetch customer details");
        return ResponseEntity.ok(carLeaseServices.calculateCarLease(leaseRequest));
    }
}
