package com.carlease.lease.repository;

import com.carlease.lease.model.response.LeaseResponse;
import com.carlease.lease.persistence.CarLeaseDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Car Lease Repository class
 */
@Repository
public interface CareLeaseRepository extends JpaRepository<CarLeaseDao,Integer> {
    /**
     * Find lease details by customer id
     * @param customerId the customer id for which lease details will be retrieved
     * @return list of car lease dao objects
     */
    List<CarLeaseDao> findByCustomerId(Integer customerId);

    /**
     * Find lease details by car id
     * @param carId the car id for which lease details will be retrieved
     * @return the lease object
     */
    CarLeaseDao findByCarId(Integer carId);
}
