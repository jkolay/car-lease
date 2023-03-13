package com.carlease.lease.model.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Lease Error Model class for to display error to user
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeaseErrorModel {
    private String description;
    private String code;
    private ErrorSeverityLevelCodeType severityLevel;

}
