package com.carlease.lease.repository;

import com.carlease.lease.model.response.LeaseResponse;
import com.carlease.lease.persistence.CarLeaseDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareLeaseRepository extends JpaRepository<CarLeaseDao,Integer> {
    List<CarLeaseDao> findByCustomerId(Integer customerId);
}
