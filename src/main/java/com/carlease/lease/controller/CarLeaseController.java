package com.carlease.lease.controller;

import com.carlease.lease.exception.CarLeaseException;
import com.carlease.lease.exception.CarNotFoundException;
import com.carlease.lease.exception.CustomerNotFoundException;
import com.carlease.lease.model.request.LeaseRequest;
import com.carlease.lease.model.response.LeaseCalculationResponse;
import com.carlease.lease.model.response.LeaseResponse;
import com.carlease.lease.service.CarLeaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/car-lease")
public class CarLeaseController {

    @Autowired
    CarLeaseService carLeaseServices;

    @PostMapping("createLease")
    public ResponseEntity<LeaseResponse> createLease(@RequestBody LeaseRequest leaseRequest) throws CarLeaseException {
        log.info("Entered client endpoint to fetch customer details");
        return ResponseEntity.ok(carLeaseServices.createNewLease(leaseRequest));
    }

    @GetMapping("calculate_lease/customer/{customerId}")
    public ResponseEntity<List<LeaseCalculationResponse>> getMonthlyLease(@PathVariable("customerId")Integer customerId) throws Exception {
        log.info("Entered client endpoint to fetch customer details");
        return ResponseEntity.ok(carLeaseServices.getLeaseAmountByCustomerId(customerId));
    }
}
