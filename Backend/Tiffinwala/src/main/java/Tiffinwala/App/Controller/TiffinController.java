package Tiffinwala.App.Controller;

import Tiffinwala.App.Dummy.TiffinDummy;
import Tiffinwala.App.Entities.Tiffin;
import Tiffinwala.App.Services.TiffinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tiffins")
public class TiffinController {

    private final TiffinService tiffinService;

    public TiffinController(TiffinService tiffinService) {
        this.tiffinService = tiffinService;
    }

    // Create a new tiffin
    @PostMapping
    public ResponseEntity<Tiffin> addTiffin( @RequestBody TiffinDummy dummy) {

        Tiffin tiffin = tiffinService.addTiffin(dummy);    
        
        return new ResponseEntity<>(tiffin,HttpStatus.OK);
    }

    // Get all tiffins by subscription plan ID
    @GetMapping("/plan/{planId}")
    public ResponseEntity<List<Tiffin>> getTiffinsByPlanId(@PathVariable Integer planId) {
        List<Tiffin> tiffins = tiffinService.getTiffinsByPlanId(planId);
        return new ResponseEntity<>(tiffins, HttpStatus.OK);
    }

    // Get a tiffin by ID
    @GetMapping("/{tiffinId}")
    public ResponseEntity<Tiffin> getTiffinById(@PathVariable Integer tiffinId) {
        Tiffin tiffin = tiffinService.getTiffinById(tiffinId);
        return new ResponseEntity<>(tiffin, HttpStatus.OK);
    }

    // Update a tiffin
    
    @PutMapping("/{tiffinId}")
    public ResponseEntity<Tiffin> updateTiffin( @RequestBody TiffinDummy dummy) {

        Tiffin tiffin = tiffinService.updateTiffin(dummy);
        return new ResponseEntity<>(tiffin, HttpStatus.OK);
    }

    // Delete a tiffin
    @DeleteMapping("/{tiffinId}")
    public ResponseEntity<Void> deleteTiffin(@PathVariable Integer tiffinId) {
        tiffinService.deleteTiffin(tiffinId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
