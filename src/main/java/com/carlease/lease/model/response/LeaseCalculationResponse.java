package com.carlease.lease.model.response;

import java.time.LocalDate;
import lombok.Data;

/** Lease calculation Response model class */
@Data
public class LeaseCalculationResponse {
  private Integer leaseId;
  private LocalDate leaseStartDate;
  private LocalDate leaseEndDate;
  private Integer carId;
  private Double leasePerMonth;
}
