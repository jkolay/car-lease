package com.carlease.lease.model.request;

import com.carlease.lease.config.CarLeaseValidationMessageConfig;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestModel {
    @NotBlank(message = CarLeaseValidationMessageConfig.USER_NAME_NOT_NULL)
    private String name;
    @NotBlank(message = CarLeaseValidationMessageConfig.EMAIL_NOT_NULL)
    private String email;
    @NotBlank(message = CarLeaseValidationMessageConfig.MOBILE_NOT_NULL)
    private String mobileNumber;
    @NotBlank(message = CarLeaseValidationMessageConfig.PASSWORD_NOT_NULL)
    private String pwd;
    @NotBlank(message = CarLeaseValidationMessageConfig.ROLE_NOT_NULL)
    private String role;
}
