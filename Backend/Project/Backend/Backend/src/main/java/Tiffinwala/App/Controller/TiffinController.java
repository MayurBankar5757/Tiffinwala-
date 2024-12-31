package Tiffinwala.App.Controller;

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
    public ResponseEntity<Tiffin> addTiffin(
            @RequestParam Integer planId,
            @RequestParam String day,
            @RequestParam Integer price,
            @RequestParam String foodType,
            @RequestParam String description,
            @RequestParam String image) {

        Tiffin tiffin = tiffinService.addTiffin(planId, day, price, foodType, description, image);
        return new ResponseEntity<>(tiffin, HttpStatus.CREATED);
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
    public ResponseEntity<Tiffin> updateTiffin(
            @PathVariable Integer tiffinId,
            @RequestParam String day,
            @RequestParam Integer price,
            @RequestParam String foodType,
            @RequestParam String description,
            @RequestParam String image) {

        Tiffin tiffin = tiffinService.updateTiffin(tiffinId, day, price, foodType, description, image);
        return new ResponseEntity<>(tiffin, HttpStatus.OK);
    }

    // Delete a tiffin
    @DeleteMapping("/{tiffinId}")
    public ResponseEntity<Void> deleteTiffin(@PathVariable Integer tiffinId) {
        tiffinService.deleteTiffin(tiffinId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
