package Tiffinwala.App.Repository;

import Tiffinwala.App.Entities.CustomerSubscribedPlans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerSubscribedPlansRepository extends JpaRepository<CustomerSubscribedPlans, Integer> {

    Optional<CustomerSubscribedPlans> findByUserUid(Integer uid);

    List<CustomerSubscribedPlans> findByVendorSubscriptionPlanPlanId(Integer planId);
    
    public CustomerSubscribedPlans getById(Integer id);
    
    
    List<CustomerSubscribedPlans> findByUser_Uid(Integer uid); // Fetch all plans for a specific user

}
