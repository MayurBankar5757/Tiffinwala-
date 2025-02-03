package com.Tiffinwala.TiffinwalaCrudService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tiffinwala.TiffinwalaCrudService.Entities.Tiffin;

public interface TiffinRepository extends JpaRepository<Tiffin, Integer> {

    // Fetch all tiffins for a given subscription plan
    List<Tiffin> findByVendorSubscriptionPlan_PlanId(Integer planId);
}
