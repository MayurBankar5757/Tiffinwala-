package com.Tiffinwala.TiffinwalaCrudService.Controller;

import java.util.List;

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

@RestController
@RequestMapping("/api/tiffins")
@CrossOrigin(origins = "http://localhost:3010")
public class TiffinController {

    private final TiffinService tiffinService;

    public TiffinController(TiffinService tiffinService) {
        this.tiffinService = tiffinService;
    }

    // Create a new tiffin
    
    @PostMapping("/createtiffin")
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

    // Get all tiffins by subscription plan ID
    @GetMapping("/plan/{planId}")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'CUSTOMER', 'ADMIN')") 
    public ResponseEntity<List<TiffinDTO>> getTiffinsByPlanId(@PathVariable("planId") Integer planId) {
        try {
            List<TiffinDTO> tiffins = tiffinService.getTiffinsByPlanId(planId);
            return new ResponseEntity<>(tiffins, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("No tiffins found for the given subscription plan ID");
        }
    }

    // Get a tiffin by ID
    @GetMapping("/{tiffinId}")
    @PreAuthorize("hasAnyAuthority('VENDOR', 'CUSTOMER', 'ADMIN')") 
    public ResponseEntity<Tiffin> getTiffinById(@PathVariable Integer tiffinId) {
        try {
            Tiffin tiffin = tiffinService.getTiffinById(tiffinId);
            return new ResponseEntity<>(tiffin, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Tiffin not found");
        }
    }

    // Update a tiffin
    @PutMapping("/{tiffinId}")
    @PreAuthorize("hasAuthority('VENDOR')")
    public ResponseEntity<Tiffin> updateTiffin(@PathVariable Integer tiffinId, @RequestBody TiffinDummy dummy) {
        try {
            dummy.setV_sub_Id(tiffinId);
            Tiffin tiffin = tiffinService.updateTiffin(dummy);
            return new ResponseEntity<>(tiffin, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred while updating tiffin.");
        }
    }

    // Delete a tiffin
    @DeleteMapping("/{tiffinId}")
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
