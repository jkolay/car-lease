package com.carlease.lease.controller;

import com.carlease.lease.exception.CarLeaseException;
import com.carlease.lease.exception.CarNotFoundException;
import com.carlease.lease.exception.CustomerNotFoundException;
import com.carlease.lease.model.request.LeaseRequest;
import com.carlease.lease.model.response.LeaseCalculationResponse;
import com.carlease.lease.model.response.LeaseResponse;
import com.carlease.lease.service.CarLeaseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Car Lease Controller Class. This class has the methods for lease creation and retrieval lease retrieval
 */
@Slf4j
@RestController
@RequestMapping(value = "api/v1/car-lease")
public class CarLeaseController {
    private final CarLeaseService carLeaseServices;

    public CarLeaseController(CarLeaseService carLeaseServices) {
        this.carLeaseServices = carLeaseServices;
    }

    /**
     * This is the endpoint to create a new lease
     *
     * @param leaseRequest the lease request model object to create a new lease
     * @return the response after lease has been created in the app
     * @throws CarLeaseException
     */

    @Operation(description = "create a new lease")
    @PostMapping("/createLease")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LeaseResponse> createLease(@RequestBody LeaseRequest leaseRequest) throws CarLeaseException {
        log.info("Entered client endpoint to create lease details");
        return ResponseEntity.ok(carLeaseServices.createNewLease(leaseRequest));
    }


    /**
     * This is the endpoint to retrieve lease details by customerId
     *
     * @param customerId the customer id for whom lease details needs to be retrieved
     * @return the List of lease objects for the customer
     * @throws Exception
     */
    @Operation(description = "Retrieve lease details by customer id")
    @GetMapping("/calculate_lease/customer/{customerId}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<LeaseCalculationResponse>> getMonthlyLease(@PathVariable("customerId") Integer customerId) throws CustomerNotFoundException {
        log.info("Entered client endpoint to fetch customer details");
        return ResponseEntity.ok(carLeaseServices.getLeaseAmountByCustomerId(customerId));
    }

    /**
     * This is the endpoint to retrieve lease details by carId
     *
     * @param carId the car id for which lease details needs to be retrieved
     * @return the lease details of the car
     * @throws Exception
     */
    @Operation(description = "Retrieve lease details by car id")
    @RequestMapping(method = RequestMethod.GET, value = "calculate_lease/car/{carId}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<LeaseResponse> getLeaseDetailsByCarId(@PathVariable("carId") Integer carId) throws CarNotFoundException {
        log.info("Entered client endpoint to fetch customer details");
        return ResponseEntity.ok(carLeaseServices.getLeaseAmountByCarId(carId));
    }
}
