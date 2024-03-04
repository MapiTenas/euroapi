package com.svalero.euroapi.controller;
import com.svalero.euroapi.domain.Country;
import com.svalero.euroapi.domain.ErrorResponse;
import com.svalero.euroapi.exception.CountryNotFoundException;
import com.svalero.euroapi.service.CountryService;
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
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping("/country/{countryId}")
    public Country getCountry(@PathVariable long countryId) throws CountryNotFoundException {
        Optional<Country> optionalCountry = countryService.getCountryById(countryId);
        return optionalCountry.orElseThrow(() -> new CountryNotFoundException(countryId));
    }
    @GetMapping("/countries")
    public List<Country> getAll(@RequestParam(defaultValue = "") String countryName,
                                @RequestParam(defaultValue = "") String bigFive,
                                @RequestParam(defaultValue = "0") int editionsWinned) {
        if (!countryName.isEmpty()){
            if(!bigFive.isEmpty() && editionsWinned !=0){
                return countryService.getCountryByCountryNameAndBigFiveAndEditionsWinned(countryName,Boolean.valueOf(bigFive),editionsWinned);
            }else if(!bigFive.isEmpty()){
                return countryService.getCountryByCountryNameAndBigFive(countryName, Boolean.valueOf(bigFive));
            }else if (editionsWinned !=0) {
                return countryService.getCountryByCountryNameAndEditionsWinned(countryName, editionsWinned);
            } else {
                return countryService.getCountryByCountryName(countryName);
            }
        } else if (!bigFive.isEmpty()) {
            if (editionsWinned !=0) {
                return countryService.getCountryByBigFiveAndEditionsWinned(Boolean.valueOf(bigFive), editionsWinned);
            } else {
                return countryService.getCountryByBigFive(Boolean.valueOf(bigFive));
            }
        } else if (editionsWinned != 0) {
            return countryService.getCountryByEditionsWinned(editionsWinned);
        } else {
            return countryService.getCountries();
        }
    }

    @PostMapping("/countries")
    public ResponseEntity<Country> saveCountry (@Valid @RequestBody Country country) {
        countryService.saveCountry(country);
        return new ResponseEntity<>(country, HttpStatus.CREATED);
    }

    @DeleteMapping("/country/{countryId}")
    public void removeCountry(@PathVariable long countryId) throws CountryNotFoundException {
        Optional<Country> optionalCountry = countryService.getCountryById(countryId);
            if(optionalCountry.isPresent()) {
                countryService.removeCountry(countryId);
            } else {
                throw new CountryNotFoundException(countryId);
            }
    }

    @PutMapping("/country/{countryId}")
    public void modifyCountry(@RequestBody Country country, @PathVariable long countryId) throws CountryNotFoundException {
        Optional<Country> optionalCountry = countryService.getCountryById(countryId);
        if (optionalCountry.isPresent()) {
            countryService.modifyCountry(country, countryId);
        } else {
            throw new CountryNotFoundException(countryId);
        }
    }

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ErrorResponse> countryNotFoundException(CountryNotFoundException cnfe) {
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
        return errors;
    }

}
