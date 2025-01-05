package Tiffinwala.App.Services;

import Tiffinwala.App.Entities.User;
import Tiffinwala.App.Entities.Vendor;
import Tiffinwala.App.Exceptions.ResourceNotFoundException;
import Tiffinwala.App.Repository.VendorRepository;
import Tiffinwala.App.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private UserRepository userRepository;

    // Save a new vendor
    public Vendor saveVendor(Integer uid, Boolean isVerified) {
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + uid));  // Fetch the user using uid
        
        Vendor vendor = new Vendor();
        vendor.setUser(user);  // Set the user object
        vendor.setIsVerified(isVerified ? true : false);  // Set verification status (0 or 1)
        return vendorRepository.save(vendor);  // Save the vendor to the repository
    }

    // Get vendor by User ID (Uid)
    public Optional<Vendor> getVendorByUserId(Integer uid) {
        return vendorRepository.findById(uid);  // Find by foreign key relationship
    }

    // Get vendor by vid
    public Vendor getVendorById(Integer vid) {
       Vendor v =    vendorRepository.findById(vid).get();
       
       if(v != null) {
    	   return v;
       }
       else {
    	   System.out.println("Vendor cannot found");
    	   return null;
       }
       
    }
    
    // Get all vendors
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    // Change verification status of vendor
    public Vendor updateVerificationStatus(Integer vendorId, Boolean isVerified) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with ID: " + vendorId));  // Proper exception handling
        vendor.setIsVerified(isVerified ? true : false);  // Set the verification status (0 or 1)
        return vendorRepository.save(vendor);  // Save and return the updated vendor
    }

    // Delete vendor by ID
    public void deleteVendorById(Integer vendorId) {
        vendorRepository.deleteById(vendorId);  // Delete the vendor by its ID
    }
}
