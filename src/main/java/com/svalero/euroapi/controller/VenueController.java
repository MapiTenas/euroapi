package com.svalero.euroapi.controller;
import com.svalero.euroapi.domain.ErrorResponse;
import com.svalero.euroapi.domain.dto.VenueInDto;
import com.svalero.euroapi.domain.dto.VenueOutDto;
import com.svalero.euroapi.exception.VenueNotFoundException;
import com.svalero.euroapi.service.VenueService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class VenueController {

    @Autowired
    private VenueService venueService;
    private Logger logger = LoggerFactory.getLogger(VenueController.class);


    @GetMapping("/venue/{venueId}")
    public ResponseEntity<VenueOutDto> getVenue(@PathVariable long venueId) {
        try {
            logger.info("ini GET /venue/" + venueId);
            VenueOutDto venueOutDto = venueService.getVenueById(venueId);
            logger.info("end GET /venue/" + venueId);
            return ResponseEntity.ok(venueOutDto);
        } catch (VenueNotFoundException vnfe) {
            throw new RuntimeException(vnfe);
        }
    }


    @GetMapping("/venues")
    public ResponseEntity<List<VenueOutDto>> getAll(@RequestParam(defaultValue = "") String venueName,
                              @RequestParam(defaultValue = "") String city,
                              @RequestParam(defaultValue = "") String adapted) {
        logger.info("ini GET /venues");
        List<VenueOutDto> venueOutDtoList = venueService.getVenues(venueName, city, adapted);
        logger.info("end GET /venues" );
        return ResponseEntity.ok(venueOutDtoList);
    }

    @PostMapping("/venues")
    public ResponseEntity<VenueOutDto> saveVenues (@Valid @RequestBody VenueInDto venueInDto) {
        logger.info("ini POST /venues");
        VenueOutDto venueOutDto = venueService.saveVenue(venueInDto);
        logger.info("end POST /venues");
        return ResponseEntity.status(HttpStatus.CREATED).body(venueOutDto);
    }

    @DeleteMapping("/venue/{venueId}")
    public ResponseEntity<Void> removeVenue(@PathVariable long venueId) {
        try {
            logger.info("ini DELETE /venue/" + venueId);
            venueService.removeVenue(venueId);
            logger.info("end DELETE /venue/" + venueId);
            return ResponseEntity.noContent().build();
        } catch (VenueNotFoundException vnfe) {
            throw new RuntimeException(vnfe);
        }
    }

    @PutMapping("/venue/{venueId}")
    public ResponseEntity<VenueOutDto> modifyVenue(@PathVariable long venueId, @Valid @RequestBody VenueInDto venueInDto){
       try {
           logger.info("ini PUT /venue/" + venueId);
           VenueOutDto venueOutDto = venueService.modifyVenue(venueId, venueInDto);
           logger.info("end PUT /venue/" + venueId);
           return ResponseEntity.ok(venueOutDto);
       } catch (VenueNotFoundException vnfe) {
           throw new RuntimeException(vnfe);
       }
    }

    @ExceptionHandler(VenueNotFoundException.class)
    public ResponseEntity<ErrorResponse> venueNotFoundException(VenueNotFoundException vnfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, vnfe.getMessage());
        logger.error(vnfe.getMessage(), vnfe);
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
        logger.error(ex.getMessage(), ex);
        return errors;
    }


}
