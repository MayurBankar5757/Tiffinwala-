package Tiffinwala.App.Controller;

import Tiffinwala.App.Dummy.TiffinDTO;
import Tiffinwala.App.Dummy.TiffinDummy;
import Tiffinwala.App.Entities.Tiffin;
import Tiffinwala.App.Exceptions.ConflictException;
import Tiffinwala.App.Exceptions.ResourceNotFoundException;
import Tiffinwala.App.Services.TiffinService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Void> deleteTiffin(@PathVariable Integer tiffinId) {
        try {
            tiffinService.deleteTiffin(tiffinId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Tiffin not found to delete");
        }
    }
}
