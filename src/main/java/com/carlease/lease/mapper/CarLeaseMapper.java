package com.carlease.lease.mapper;

import com.carlease.lease.model.request.LeaseRequest;
import com.carlease.lease.model.response.CarResponse;
import com.carlease.lease.model.response.CustomerResponse;
import com.carlease.lease.model.response.LeaseCalculationResponse;
import com.carlease.lease.model.response.LeaseResponse;
import com.carlease.lease.persistence.CarLeaseDao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface CarLeaseMapper {
    /**
     * map lease request object to lease dao
     * @param leaseRequest
     * @return
     */
  CarLeaseDao mapLeaseRequestToCarLeaseDao(LeaseRequest leaseRequest);

    /**
     * Map car lease dao to lease response object
     * @param carLeaseDao
     * @return
     */
  LeaseCalculationResponse mapCarLeaseDaoToLeaseCalcResponse(CarLeaseDao carLeaseDao);

    /**
     * Map car lease dao to lease response
     * @param leaseDao
     * @param carResponse
     * @param customerResponse
     * @return
     */
  @Mapping(target = "customer", source = "customerResponse")
  @Mapping(target = "car", source = "carResponse")
  LeaseResponse mapCarLeaseDaoToLeaseResponse(
      CarLeaseDao leaseDao, CarResponse carResponse, CustomerResponse customerResponse);
}
