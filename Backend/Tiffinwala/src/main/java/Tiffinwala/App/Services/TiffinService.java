package Tiffinwala.App.Services;

import Tiffinwala.App.Entities.Tiffin;
import Tiffinwala.App.Entities.VendorSubscriptionPlan;
import Tiffinwala.App.Repository.TiffinRepository;
import Tiffinwala.App.Repository.VendorSubscriptionPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TiffinService {

    private final TiffinRepository tiffinRepository;
    private final VendorSubscriptionPlanRepository vendorSubscriptionPlanRepository;

    public TiffinService(TiffinRepository tiffinRepository, VendorSubscriptionPlanRepository vendorSubscriptionPlanRepository) {
        this.tiffinRepository = tiffinRepository;
        this.vendorSubscriptionPlanRepository = vendorSubscriptionPlanRepository;
    }

    // Add a new tiffin
    public Tiffin addTiffin(Integer planId, String day, Integer price, String foodType, String description, String image) {
        VendorSubscriptionPlan subscriptionPlan = vendorSubscriptionPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Vendor Subscription Plan not found"));
        Tiffin tiffin = new Tiffin(subscriptionPlan, day, price, foodType, description, image);
        return tiffinRepository.save(tiffin);
    }

    // Get all tiffins by subscription plan ID
    public List<Tiffin> getTiffinsByPlanId(Integer planId) {
        return tiffinRepository.findByVendorSubscriptionPlanPlanId(planId);
    }

    // Get a tiffin by ID
    public Tiffin getTiffinById(Integer tiffinId) {
        return tiffinRepository.findById(tiffinId).orElseThrow(() -> new IllegalArgumentException("Tiffin not found"));
    }

    // Update a tiffin
    public Tiffin updateTiffin(Integer tiffinId, String day, Integer price, String foodType, String description, String image) {
        Tiffin tiffin = getTiffinById(tiffinId);
        tiffin.setDay(day);
        tiffin.setPrice(price);
        tiffin.setFoodType(foodType);
        tiffin.setDescription(description);
        tiffin.setImage(image);
        return tiffinRepository.save(tiffin);
    }

    // Delete a tiffin by ID
    public void deleteTiffin(Integer tiffinId) {
        tiffinRepository.deleteById(tiffinId);
    }
}
