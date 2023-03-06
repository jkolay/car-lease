package com.carlease.lease.repository;

import com.carlease.lease.persistence.CarLeaseDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareLeaseRepository extends JpaRepository<CarLeaseDao,Integer> {
}
