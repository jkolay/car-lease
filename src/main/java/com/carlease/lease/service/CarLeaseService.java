package com.carlease.lease.service;

import com.carlease.lease.exception.CarLeaseException;
import com.carlease.lease.exception.CarNotFoundException;
import com.carlease.lease.exception.CustomerNotFoundException;
import com.carlease.lease.model.request.LeaseRequest;
import com.carlease.lease.model.response.LeaseCalculationResponse;
import com.carlease.lease.model.response.LeaseResponse;

import java.util.List;

public interface CarLeaseService {

        LeaseResponse createNewLease(LeaseRequest leaseRequest) throws CarLeaseException;

        List<LeaseCalculationResponse> getLeaseAmountByCustomerId(Integer customerId) throws CustomerNotFoundException;
}
