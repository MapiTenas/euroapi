package com.svalero.euroapi.controller;
import com.svalero.euroapi.domain.Venue;
import com.svalero.euroapi.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VenueController {

    @Autowired
    private VenueService venueService;

    @GetMapping("/venues")
    public List<Venue> getAll(){return venueService.getVenues(); }

    @PostMapping("/venues")
    public void saveVenues (@RequestBody Venue venue) {
        venueService.saveVenue(venue);
    }
    @DeleteMapping("/venue/{venueId}")
    public void removeVenue(@PathVariable long venueId) {
        venueService.removeVenue(venueId);
    }

    @PutMapping("/venue/{venueId}")
    public void modifyVenue(@RequestBody Venue venue, @PathVariable long venueId) {
        venueService.modifyVenue(venue, venueId);
    }


}
