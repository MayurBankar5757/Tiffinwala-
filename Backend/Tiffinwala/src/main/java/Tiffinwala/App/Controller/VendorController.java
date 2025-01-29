package Tiffinwala.App.Controller;
import Tiffinwala.App.Dummy.RegDummy;
import Tiffinwala.App.Entities.Address;
import Tiffinwala.App.Entities.Role;
import Tiffinwala.App.Entities.User;
import Tiffinwala.App.Entities.Vendor;
import Tiffinwala.App.Entities.VendorSubscriptionPlan;
import Tiffinwala.App.Repository.UserRepository;
import Tiffinwala.App.Repository.VendorRepository;
import Tiffinwala.App.Services.RoleServices;
import Tiffinwala.App.Services.VendorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@CrossOrigin(origins ="http://localhost:3000")
public class VendorController {
	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private VendorRepository vendorRepository;

	    @Autowired
    private  VendorService vendorService;
    @Autowired
    private RoleServices rserv;

   

    // Get all Vendors
    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }

   

    // Change Vendor Verification Status for admin
    @PutMapping("/{id}/verify")
    public ResponseEntity<Vendor> updateVerificationStatus(
            @PathVariable Integer id, @RequestParam Boolean isVerified) {
        Vendor updatedVendor = vendorService.updateVerificationStatus(id, isVerified);
        return new ResponseEntity<>(updatedVendor, HttpStatus.OK);
    }
    
    
    
   
    
    
    @GetMapping("/vendor/{uid}") 
    public Vendor getVendorByUserUid(@PathVariable Integer uid) {
        return vendorRepository.findVendorByUserUid(uid);
    }
    

    @PostMapping("/RegUser")
    public ResponseEntity<?> saveUser(@RequestBody RegDummy r) {
        try {
            User user = new User();
            user.setFname(r.getFname());
            user.setLname(r.getLname());
            user.setEmail(r.getEmail());
            user.setPassword(r.getPassword());
            user.setContact(r.getContact());

            Address address = new Address(r.getCity(), r.getState(), r.getPincode(), r.getArea());
            user.setAddress(address);

            Role role = rserv.getRoleById(r.getRid());
            if (role == null) {
                return ResponseEntity.badRequest().body("Invalid role ID.");
            }
            user.setRole(role);

            userRepository.save(user);

            if (r.getRid() == 2) { // Vendor
                Vendor vendor = new Vendor();
                vendor.setIsVerified(false);
                vendor.setFoodLicenceNo(r.getFoodLicenceNo());
                vendor.setAdhar_no(r.getAdhar_no());
                vendor.setUser(user);
                vendorRepository.save(vendor);
            }

            return ResponseEntity.ok("User registered successfully.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving user: " + e.getMessage());
        }
    }
    
    // get all approved vendor for admin
    
    @GetMapping("/getAllApprovedVendor")
    public ResponseEntity<List<Vendor>> getAllApprovedVendors() {
        List<Vendor> vendors = vendorService.getAllApprovedVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }
    

    // get all upapproved vendor for admin
    @GetMapping("/getBlockedVendors")
    public ResponseEntity<List<Vendor>> getAllUnapprovedVendors() {
        List<Vendor> vendors = vendorService.getAllUnapprovedVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }
    
    

    
    
   
    
}





















// get User by User Id
//@GetMapping("/vendor/{uid}")
//public Object getVendorData(@PathVariable Integer uid) {
//  // Fetch user data
//  //User user = (userRepository.findById(uid).get());
//  User user = userRepository.findById(uid).orElse(null);
//  // Fetch vendor data based on user id
//  Vendor vendor = vendorRepository.findByUserUid(uid); 
//
//  if (user != null && vendor != null) {
//      // Combine both user and vendor data
//      var response = new Object() {
//          public Integer uid = user.getUid();
//          public String fname = user.getFname();
//          public String lname = user.getLname();
//          public String email = user.getEmail();
//          public String password = user.getPassword();
//          public String contact = user.getContact();
//          public Address address = user.getAddress();
//          public Role role = user.getRole();
//          public Boolean isVerified = vendor.getIsVerified();
//          public Integer vendorId = vendor.getVendorId();
//      };
//      
//      return response;
//  }
//  
//  return null; // Return null if user or vendor not found
//}




// Create Vendor (This will be triggered automatically when a user with the "Vendor" role is added via trigger)
//@PostMapping
//public ResponseEntity<Vendor> createVendor(@RequestParam Integer uid, @RequestParam Boolean isVerified) {
//    Vendor savedVendor = vendorService.saveVendor(uid, isVerified);
//    return new ResponseEntity<>(savedVendor, HttpStatus.CREATED);
//}
