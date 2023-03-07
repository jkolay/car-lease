package com.carlease.lease.model.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaseCalculationResponse {
    private Integer leaseId;
    private LocalDate leaseStartDate;
    private LocalDate leaseEndDate;
    private Integer carId;
    private Double leasePerMonth;
}
