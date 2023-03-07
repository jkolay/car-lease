package com.carlease.lease.services;

import com.carlease.lease.leaseDto.LeaseRequest;
import com.carlease.lease.leaseDto.LeaseResponse;

public interface CarLeaseServices {
        LeaseResponse calculateCarLease(LeaseRequest leaseRequest);

    //    CarResponse getCarDetails(String country);
}
