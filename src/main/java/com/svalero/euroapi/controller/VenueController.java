package com.svalero.euroapi.controller;
import com.svalero.euroapi.domain.ErrorResponse;
import com.svalero.euroapi.domain.Venue;
import com.svalero.euroapi.domain.dto.VenueInDto;
import com.svalero.euroapi.domain.dto.VenueOutDto;
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
    public ResponseEntity<VenueOutDto> getVenue(@PathVariable long venueId) {
        try {
            VenueOutDto venueOutDto = venueService.getVenueById(venueId);
            return ResponseEntity.ok(venueOutDto);
        } catch (VenueNotFoundException vnfe) {
            throw new RuntimeException(vnfe);
        }
    }


    @GetMapping("/venues")
    public ResponseEntity<List<VenueOutDto>> getAll(@RequestParam(defaultValue = "") String venueName,
                              @RequestParam(defaultValue = "") String city,
                              @RequestParam(defaultValue = "") String adapted) {
        List<VenueOutDto> venueOutDtoList = venueService.getVenues(venueName, city, adapted);
        return ResponseEntity.ok(venueOutDtoList);
    }

    @PostMapping("/venues")
    public ResponseEntity<VenueOutDto> saveVenues (@Valid @RequestBody VenueInDto venueInDto) {
        VenueOutDto venueOutDto = venueService.saveVenue(venueInDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(venueOutDto);
    }

    @DeleteMapping("/venue/{venueId}")
    public ResponseEntity<Void> removeVenue(@PathVariable long venueId) {
        try {
            venueService.removeVenue(venueId);
            return ResponseEntity.noContent().build();
        } catch (VenueNotFoundException vnfe) {
            throw new RuntimeException(vnfe);
        }
    }

    @PutMapping("/venue/{venueId}")
    public ResponseEntity<VenueOutDto> modifyVenue(@PathVariable long venueId, @Valid @RequestBody VenueInDto venueInDto){
       try {
           VenueOutDto venueOutDto = venueService.modifyVenue(venueId, venueInDto);
           return ResponseEntity.ok(venueOutDto);
       } catch (VenueNotFoundException vnfe) {
           throw new RuntimeException(vnfe);
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
