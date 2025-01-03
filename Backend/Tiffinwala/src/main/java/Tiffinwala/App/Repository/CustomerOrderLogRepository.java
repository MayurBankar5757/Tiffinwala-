package Tiffinwala.App.Repository;

import Tiffinwala.App.Entities.CustomerOrderLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderLogRepository extends JpaRepository<CustomerOrderLog, Integer> {
}
