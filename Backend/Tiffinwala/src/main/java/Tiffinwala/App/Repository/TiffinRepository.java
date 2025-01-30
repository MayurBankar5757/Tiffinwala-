package Tiffinwala.App.Repository;

import Tiffinwala.App.Entities.Tiffin;
import Tiffinwala.App.Entities.VendorSubscriptionPlan;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TiffinRepository extends JpaRepository<Tiffin, Integer> {

    // Fetch all tiffins for a given subscription plan
    List<Tiffin> findByVendorSubscriptionPlan_PlanId(Integer planId);
}
