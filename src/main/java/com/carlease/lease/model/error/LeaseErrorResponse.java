package com.carlease.lease.model.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Lease Error response model class
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaseErrorResponse {
    private List<LeaseErrorModel> errors;
}
