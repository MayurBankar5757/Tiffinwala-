package Tiffinwala.App.Repository;

import Tiffinwala.App.Entities.Tiffin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TiffinRepository extends JpaRepository<Tiffin, Integer> {

    // Fetch all tiffins for a given subscription plan
    List<Tiffin> findByVendorSubscriptionPlanPlanId(Integer planId);
}
