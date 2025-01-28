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

    private  VendorService vendorService;
    @Autowired
    private RoleServices rserv;

   

    // Get all Vendors
    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }

   

    // Change Vendor Verification Status
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
       
        User user = new User();
        user.setFname(r.getFname());
        user.setLname(r.getLname());
        user.setEmail(r.getEmail());
        user.setPassword(r.getPassword());
        user.setContact(r.getContact());
        // address set
        Address a = new Address(r.getCity(), r.getState(),r.getPincode(), r.getArea());
        
        // getting role
      Role role = rserv.getRoleById(r.getRid());
        user.setRole(role);
        
        user.setAddress(a);

     
        if (r.getRid() == 2) { // Vendor Role
            Vendor vendor = new Vendor();
            vendor.setIsVerified(false);
            vendor.setFoodLicenceNo(r.getFoodLicenceNo());
            vendor.setAdhar_no(r.getAdhar_no());
            vendor.setUser(user); 
            userRepository.save(user);
            vendorRepository.save(vendor); 
        } else if (r.getRid() == 3) { 
            userRepository.save(user); 
        } else {
            return ResponseEntity.badRequest().body("Invalid role ID.");
        }

        return ResponseEntity.ok("User saved successfully.");
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
