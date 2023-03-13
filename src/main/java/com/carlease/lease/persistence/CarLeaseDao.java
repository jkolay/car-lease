package com.carlease.lease.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Car Lease Entity Class. This class object stores car id, and customer id along with other details
 */
@Entity
@DynamicUpdate
@Table(name = "lease")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarLeaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incrementDomain")
    @GenericGenerator(name = "incrementDomain", strategy = "increment")
    private Integer leaseId;

    @Column(nullable = false)
    private Integer carId;

    @Column
    private Integer customerId;

    @Column
    private Integer duration;

    @Column
    private LocalDate leaseStartDate;

    @Column
    private LocalDate leaseEndDate;

    @Column
    private Double interestRate;

    @Column
    private Long carMileageAtStartOfLease;

    @Column
    private Long defaultAllottedMileage;

    @Column
    private Double leasePerMonth;


    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
