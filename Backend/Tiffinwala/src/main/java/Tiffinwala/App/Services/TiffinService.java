package Tiffinwala.App.Services;

import Tiffinwala.App.Dummy.TiffinDTO;
import Tiffinwala.App.Dummy.TiffinDummy;
import Tiffinwala.App.Entities.Tiffin;
import Tiffinwala.App.Entities.VendorSubscriptionPlan;
import Tiffinwala.App.Repository.TiffinRepository;
import Tiffinwala.App.Repository.VendorSubscriptionPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TiffinService {

    private final TiffinRepository tiffinRepository;
    private final VendorSubscriptionPlanRepository vendorSubscriptionPlanRepository;

    public TiffinService(TiffinRepository tiffinRepository, VendorSubscriptionPlanRepository vendorSubscriptionPlanRepository) {
        this.tiffinRepository = tiffinRepository;
        this.vendorSubscriptionPlanRepository = vendorSubscriptionPlanRepository;
    }

    // Add a new tiffin
    
    public Tiffin addTiffin(TiffinDummy dummy) {
    	
        VendorSubscriptionPlan subscriptionPlan = vendorSubscriptionPlanRepository.findById(dummy.getV_sub_Id())
                .orElseThrow(() -> new IllegalArgumentException("Vendor Subscription Plan not found"));
        Tiffin tiffin = new Tiffin();
        tiffin.setDay(dummy.getDay());
        tiffin.setDescription(dummy.getDescription());
        tiffin.setFoodType(dummy.getFoodType());
        tiffin.setName(dummy.getName());
        tiffin.setVendorSubscriptionPlan(subscriptionPlan);
        
        return tiffinRepository.save(tiffin);
    }

    // Get all tiffins by subscription plan ID
    public List<TiffinDTO> getTiffinsByPlanId(Integer planId) {
        List<Tiffin> tiffins = tiffinRepository.findByVendorSubscriptionPlan_PlanId(planId);
        return tiffins.stream().map(TiffinDTO::new).collect(Collectors.toList());
    }
    

    // Get a tiffin by ID
    public Tiffin getTiffinById(Integer tiffinId) {
        return tiffinRepository.findById(tiffinId).orElseThrow(() -> new IllegalArgumentException("Tiffin not found"));
    }

    // Update a tiffin
    public Tiffin updateTiffin(TiffinDummy dummy) {
        Tiffin tiffin = getTiffinById(dummy.getV_sub_Id());
        
        tiffin.setDay(dummy.getDay());
        
        tiffin.setFoodType(dummy.getFoodType());
        tiffin.setDescription(dummy.getDescription());
        tiffin.setName(dummy.getName());
        
        return tiffinRepository.save(tiffin);
    }

    // Delete a tiffin by ID
    public void deleteTiffin(Integer tiffinId) {
        tiffinRepository.deleteById(tiffinId);
    }
}
