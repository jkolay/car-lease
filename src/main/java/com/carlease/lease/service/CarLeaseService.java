package com.carlease.lease.service;

import com.carlease.lease.exception.CarLeaseException;
import com.carlease.lease.exception.CarNotFoundException;
import com.carlease.lease.exception.CustomerNotFoundException;
import com.carlease.lease.model.request.LeaseRequest;
import com.carlease.lease.model.response.LeaseCalculationResponse;
import com.carlease.lease.model.response.LeaseResponse;

import java.util.List;

/**
 * The car lease service class
 */
public interface CarLeaseService {
        /**
         * Creates the new lease
         * @param leaseRequest the lease model object
         * @return
         * @throws CarLeaseException
         */
        LeaseResponse createNewLease(LeaseRequest leaseRequest) throws CarLeaseException;

        /**
         * Retrieves lease details by customer id
         * @param customerId
         * @return the list of lease details
         * @throws CustomerNotFoundException
         */
        List<LeaseCalculationResponse> getLeaseAmountByCustomerId(Integer customerId) throws CustomerNotFoundException;

        /**
         * Retrieves lease details by car
         * @param carId the car id
         * @return
         * @throws CarNotFoundException
         */
        LeaseResponse getLeaseAmountByCarId(Integer carId) throws  CarNotFoundException;

        void setValue(String username, String pwd);
}
