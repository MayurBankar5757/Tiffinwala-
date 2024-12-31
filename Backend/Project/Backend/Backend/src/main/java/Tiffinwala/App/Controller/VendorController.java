package Tiffinwala.App.Controller;

import Tiffinwala.App.Entities.Vendor;
import Tiffinwala.App.Services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    // Create Vendor (This will be triggered automatically when a user with the "Vendor" role is added via trigger)
//    @PostMapping
//    public ResponseEntity<Vendor> createVendor(@RequestParam Integer uid, @RequestParam Boolean isVerified) {
//        Vendor savedVendor = vendorService.saveVendor(uid, isVerified);
//        return new ResponseEntity<>(savedVendor, HttpStatus.CREATED);
//    }

    // Get Vendor by User ID
    @GetMapping("/user/{uid}")
    public ResponseEntity<Vendor> getVendorByUserId(@PathVariable Integer uid) {
        return vendorService.getVendorByUserId(uid)
                .map(vendor -> new ResponseEntity<>(vendor, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get all Vendors
    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }

    // Delete Vendor by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Integer id) {
        vendorService.deleteVendorById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Change Vendor Verification Status
    @PutMapping("/{id}/verify")
    public ResponseEntity<Vendor> updateVerificationStatus(
            @PathVariable Integer id, @RequestParam Boolean isVerified) {
        Vendor updatedVendor = vendorService.updateVerificationStatus(id, isVerified);
        return new ResponseEntity<>(updatedVendor, HttpStatus.OK);
    }
}
