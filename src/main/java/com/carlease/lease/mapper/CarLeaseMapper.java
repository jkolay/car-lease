package com.carlease.lease.mapper;

import com.carlease.lease.config.CarLeaseConstant;
import com.carlease.lease.model.request.LeaseRequest;
import com.carlease.lease.model.response.CarResponse;
import com.carlease.lease.model.response.CustomerResponse;
import com.carlease.lease.model.response.LeaseCalculationResponse;
import com.carlease.lease.model.response.LeaseResponse;
import com.carlease.lease.persistence.CarLeaseDao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.text.DecimalFormat;
import java.time.LocalDate;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface CarLeaseMapper {

    CarLeaseDao mapLeaseRequestToCarLeaseDao(LeaseRequest leaseRequest);

    LeaseCalculationResponse mapCarLeaseDaoToLeaseCalcResponse(CarLeaseDao carLeaseDao);


    @Mapping(target = "customer", source = "customerResponse")
    @Mapping(target = "car", source = "carResponse")
    LeaseResponse mapCarLeaseDaoToLeaseResponse(CarLeaseDao leaseDao, CarResponse carResponse, CustomerResponse customerResponse);
}
