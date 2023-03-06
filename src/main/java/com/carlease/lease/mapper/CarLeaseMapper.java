package com.carlease.lease.mapper;

import com.carlease.lease.model.request.LeaseRequest;
import com.carlease.lease.model.response.LeaseResponse;
import com.carlease.lease.persistence.CarLeaseDao;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface CarLeaseMapper {

    CarLeaseDao mapLeaseRequestToCarLeaseDao(LeaseRequest leaseRequest);

    LeaseResponse mapCarLeaseDaoToLeaseResponse(CarLeaseDao carLeaseDao);
}
