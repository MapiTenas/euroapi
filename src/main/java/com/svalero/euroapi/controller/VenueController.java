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
    public List<Venue> getAll(@RequestParam(defaultValue = "") String venueName,
                              @RequestParam(defaultValue = "") String city,
                              @RequestParam(defaultValue = "") String adapted) {
        if (!venueName.isEmpty()){
            if (!city.isEmpty() && !adapted.isEmpty()) {
                return venueService.getVenueByVenueNameAndCityAndAdapted(venueName,city,Boolean.valueOf(adapted));
            } else if (!city.isEmpty()) {
                return venueService.getVenueByVenueNameAndCity(venueName,city);
            } else if (!adapted.isEmpty()){
                return venueService.getVenueByVenueNameAndAdapted(venueName, Boolean.valueOf(adapted));
            } else {
                return venueService.getVenueByVenueName(venueName);
            }
        } else if (!city.isEmpty()) {
            if (!adapted.isEmpty()) {
                return venueService.getVenueByCityAndAdapted(city, Boolean.valueOf(adapted));
            } else {
                return venueService.getVenueByCity(city);
            }
        } else if (!adapted.isEmpty()){
            return venueService.getVenueByAdapted(Boolean.valueOf(adapted));
        } else {
         return venueService.getVenues();
        }
    }






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
