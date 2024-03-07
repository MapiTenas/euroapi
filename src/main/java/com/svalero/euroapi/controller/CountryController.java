package com.svalero.euroapi.controller;
import com.svalero.euroapi.domain.ErrorResponse;
import com.svalero.euroapi.domain.dto.CountryInDto;
import com.svalero.euroapi.domain.dto.CountryOutDto;
import com.svalero.euroapi.exception.CountryNotFoundException;
import com.svalero.euroapi.service.CountryService;
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
public class CountryController {
    @Autowired
    private CountryService countryService;
    private Logger logger = LoggerFactory.getLogger(CountryController.class);


    @GetMapping("/country/{countryId}")
    public ResponseEntity<CountryOutDto> getCountry(@PathVariable long countryId){
        try {
            logger.info("ini GET /country/" + countryId);
            CountryOutDto countryOutDto = countryService.getCountryById(countryId);
            logger.info("end GET /country/"+ countryId);
            return ResponseEntity.ok(countryOutDto);
        } catch (CountryNotFoundException cnfc) {
            throw new RuntimeException(cnfc);
        }
    }
    @GetMapping("/countries")
    public ResponseEntity<List<CountryOutDto>> getCountries(@RequestParam(defaultValue = "") String countryName,
                                      @RequestParam(defaultValue = "") String bigFive,
                                      @RequestParam(defaultValue = "0") int editionsWinned) {
        logger.info("ini GET /countries");
        List<CountryOutDto> countryOutDtoList = countryService.getCountries(countryName, bigFive, editionsWinned);
        logger.info("ini GET /countries");
        return ResponseEntity.ok(countryOutDtoList);
    }

    @PostMapping("/countries")
    public ResponseEntity<CountryOutDto> saveCountry (@Valid @RequestBody CountryInDto countryInDto) {
        logger.info("ini POST /countries");
        CountryOutDto countryOutDto = countryService.saveCountry(countryInDto);
        logger.info("end POST /countries");
        return ResponseEntity.status(HttpStatus.CREATED).body(countryOutDto);
    }

    @DeleteMapping("/country/{countryId}")
    public ResponseEntity<Void> removeCountry(@PathVariable long countryId) {
        try {
            logger.info("ini DELETE /country/" + countryId);
            countryService.removeCountry(countryId);
            logger.info("end DELETE /country/" + countryId);
            return ResponseEntity.noContent().build();
        } catch (CountryNotFoundException cnfe) {
            throw new RuntimeException(cnfe);
        }
    }

    @PutMapping("/country/{countryId}")
    public ResponseEntity<CountryOutDto> modifyCountry(@PathVariable long countryId, @Valid @RequestBody CountryInDto countryInDto){
        try {
            logger.info("ini PUT /country/" + countryId);
            CountryOutDto countryOutDto = countryService.modifyCountry(countryId, countryInDto);
            logger.info("end PUT /country/" + countryId);
            return ResponseEntity.ok(countryOutDto);
        } catch (CountryNotFoundException cnfe) {
            throw new RuntimeException(cnfe);
        }
    }

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ErrorResponse> countryNotFoundException(CountryNotFoundException cnfe) {
        logger.error(cnfe.getMessage(), cnfe);
        ErrorResponse errorResponse = new ErrorResponse(404, cnfe.getMessage());
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
