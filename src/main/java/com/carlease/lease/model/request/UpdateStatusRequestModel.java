package com.carlease.lease.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Status update request model to update status of Car and Customer Object
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusRequestModel {

    private String status;
}
