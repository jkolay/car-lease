package com.carlease.lease.model.request;

import com.carlease.lease.config.CarLeaseValidationMessageConfig;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class LeaseRequest {

    @NotNull(message= CarLeaseValidationMessageConfig.CUSTOMER_ID_NOT_NULL)
    @Schema(description = "Customer id who wants to lease the car", example = "1")
    private Integer customerId;
    @NotNull(message= CarLeaseValidationMessageConfig.CAR_ID_NOT_NULL)
    @Schema(description = "Car id which needs to be lease", example = "1")
    private Integer carId;
    @NotNull(message= CarLeaseValidationMessageConfig.DURATION_NOT_NULL)
    @Schema(description = "Duration of the lease in months", example = "14")
    private Integer duration;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate leaseStartDate;



}
