package com.Tiffinwala.TiffinwalaCrudService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Tiffinwala.TiffinwalaCrudService.Entities.CustomerOrderLog;

@Repository
public interface CustomerOrderLogRepository extends JpaRepository<CustomerOrderLog, Integer> {
}
