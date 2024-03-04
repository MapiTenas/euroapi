package com.svalero.euroapi.controller;
import com.svalero.euroapi.domain.ErrorResponse;
import com.svalero.euroapi.domain.Song;
import com.svalero.euroapi.domain.Venue;
import com.svalero.euroapi.exception.SongNotFoundException;
import com.svalero.euroapi.exception.VenueNotFoundException;
import com.svalero.euroapi.service.VenueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class VenueController {

    @Autowired
    private VenueService venueService;

    @GetMapping("/venue/{venueId}")
    public Venue getVenue(@PathVariable long venueId) throws VenueNotFoundException {
        Optional<Venue> optionalVenue = venueService.getVenueById(venueId);
        return optionalVenue.orElseThrow(() -> new VenueNotFoundException(venueId));
    }

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
    public ResponseEntity<Venue> saveVenues (@Valid @RequestBody Venue venue) {
        venueService.saveVenue(venue);
        return new ResponseEntity<>(venue, HttpStatus.CREATED);
    }
    @DeleteMapping("/venue/{venueId}")
    public void removeVenue(@PathVariable long venueId) throws VenueNotFoundException {
        Optional<Venue> optionalVenue = venueService.getVenueById(venueId);
            if (optionalVenue.isPresent()) {
                venueService.removeVenue(venueId);
            } else {
                throw new VenueNotFoundException(venueId);
            }
    }

    @PutMapping("/venue/{venueId}")
    public void modifyVenue(@RequestBody Venue venue, @PathVariable long venueId) throws VenueNotFoundException {
        Optional<Venue> optionalVenue = venueService.getVenueById(venueId);
            if (optionalVenue.isPresent()) {
                venueService.modifyVenue(venue, venueId);
            } else {
                throw new VenueNotFoundException(venueId);
            }
    }

    @ExceptionHandler(VenueNotFoundException.class)
    public ResponseEntity<ErrorResponse> venueNotFoundException(VenueNotFoundException vnfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, vnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
