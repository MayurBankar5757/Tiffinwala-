package com.Tiffinwala.TiffinwalaCrudService.Controller;

import java.util.List;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Tiffinwala.TiffinwalaCrudService.Dummy.TiffinDTO;
import com.Tiffinwala.TiffinwalaCrudService.Dummy.TiffinDummy;
import com.Tiffinwala.TiffinwalaCrudService.Entities.Tiffin;
import com.Tiffinwala.TiffinwalaCrudService.Exceptions.ConflictException;
import com.Tiffinwala.TiffinwalaCrudService.Exceptions.ResourceNotFoundException;
import com.Tiffinwala.TiffinwalaCrudService.Services.TiffinService;

import ch.qos.logback.classic.Logger;

@RestController
@RequestMapping("/api2")
//@CrossOrigin(origins = "http://localhost:3010")
public class TiffinController {

    private final TiffinService tiffinService;

    public TiffinController(TiffinService tiffinService) {
        this.tiffinService = tiffinService;
    }

    // Create a new tiffin
    
    @PostMapping("/tiffin/createtiffin")
    @PreAuthorize("hasAuthority('VENDOR')")
    public ResponseEntity<Tiffin> addTiffin(@RequestBody TiffinDummy dummy) {
        try {
            Tiffin tiffin = tiffinService.addTiffin(dummy);
            return new ResponseEntity<>(tiffin, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Duplicate entry detected: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred while creating tiffin.");
        }
    }

// // Update a tiffin
//    @PutMapping("/{tiffinId}")
//    @PreAuthorize("hasAuthority('VENDOR')")
//    public ResponseEntity<Tiffin> updateTiffin(@PathVariable Integer tiffinId, @RequestBody TiffinDummy dummy) {
//        try {
//            dummy.setV_sub_Id(tiffinId);
//            Tiffin tiffin = tiffinService.updateTiffin(dummy);
//            return new ResponseEntity<>(tiffin, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new RuntimeException("Unexpected error occurred while updating tiffin.");
//        }
//    }
//   

    @PutMapping("/tiffin/updateTiffin/{tiffinId}")
    @PreAuthorize("hasAuthority('VENDOR')")
    public ResponseEntity<Tiffin> updateTiffin(@PathVariable("tiffinId") Integer tiffinId, 
                                               @RequestBody TiffinDummy dummy) {
        try {
            Tiffin updatedTiffin = tiffinService.updateTiffin(tiffinId, dummy);
            return ResponseEntity.ok(updatedTiffin);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage()); // Tiffin or Plan not found
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Duplicate entry detected: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred while updating tiffin.");
        }
    }

    
    
    // Get all tiffins by subscription plan ID
    @GetMapping("/tiffin/plan/{planId}")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'CUSTOMER', 'ADMIN')") 
    public ResponseEntity<List<TiffinDTO>> getTiffinsByPlanId(@PathVariable("planId") Integer planId) {
    	System.out.println("getTiffinsByPlanId hit");

        try {
            List<TiffinDTO> tiffins = tiffinService.getTiffinsByPlanId(planId);
            return new ResponseEntity<>(tiffins, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("No tiffins found for the given subscription plan ID");
        }
    }

    // Get a tiffin by ID
    @GetMapping("/tiffin/{tiffinId}")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'CUSTOMER', 'ADMIN')") 
    public ResponseEntity<Tiffin> getTiffinById(@PathVariable Integer tiffinId) {
        try {
            Tiffin tiffin = tiffinService.getTiffinById(tiffinId);
            return new ResponseEntity<>(tiffin, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Tiffin not found");
        }
    }

    

    // Delete a tiffin
    @DeleteMapping("/tiffin/{tiffinId}")
    @PreAuthorize("hasAuthority('VENDOR')")
    public ResponseEntity<Void> deleteTiffin(@PathVariable Integer tiffinId) {
        try {
            tiffinService.deleteTiffin(tiffinId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Tiffin not found to delete");
        }
    }
}
