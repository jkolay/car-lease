package com.carlease.lease.mapper;

import com.carlease.lease.model.request.UserRequestModel;
import com.carlease.lease.persistence.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserMapper {

    Customer mapUserRequestModelToCustomer(UserRequestModel userRequestModel);
}
