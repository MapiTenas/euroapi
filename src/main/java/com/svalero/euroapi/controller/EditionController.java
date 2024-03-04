package com.svalero.euroapi.controller;
import com.svalero.euroapi.domain.Edition;
import com.svalero.euroapi.domain.ErrorResponse;
import com.svalero.euroapi.exception.EditionNotFoundException;
import com.svalero.euroapi.service.EditionService;
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
public class EditionController {
    @Autowired
    private EditionService editionService;

    @GetMapping("/edition/{editionId}")
    public Edition getEdition(@PathVariable long editionId) throws EditionNotFoundException {
        Optional<Edition> optionalEdition = editionService.getEditionById(editionId);
        return optionalEdition.orElseThrow(() -> new EditionNotFoundException(editionId));
    }

    @GetMapping("/editions")
    public List<Edition> getAll(@RequestParam(defaultValue = "0") int edition,
                                @RequestParam(defaultValue= "") String countryOrganizer,
                                @RequestParam(defaultValue = "") String cancelled) {
        if (edition != 0){
            if (!countryOrganizer.isEmpty() && !cancelled.isEmpty()){
                return editionService.getEditionByEditionAndCountryOrganizerAndCancelled(edition, countryOrganizer,Boolean.valueOf(cancelled));
            } else if (!countryOrganizer.isEmpty()) {
                return editionService.getEditionByEditionAndCountryOrganizer(edition,countryOrganizer);
            } else if (!cancelled.isEmpty()) {
                return editionService.getEditionByEditionAndCancelled(edition, Boolean.valueOf(cancelled));
            } else {
                return editionService.getEditionByEdition(edition);
            }
        } else if (!countryOrganizer.isEmpty()) {
            if (!cancelled.isEmpty()){
                return editionService.getEditionByCountryOrganizerAndCancelled(countryOrganizer, Boolean.valueOf(cancelled));
            } else {
                return editionService.getEditionByCountryOrganizer(countryOrganizer);
            }
        } else if (!cancelled.isEmpty()){
            return editionService.getEditionByCancelled(Boolean.valueOf(cancelled));
        } else {
            return editionService.getEditions();
        }
    }

    @PostMapping("/editions")
    public ResponseEntity<Edition> saveEdition (@Valid @RequestBody Edition edition) {
        editionService.saveEdition(edition);
        return new ResponseEntity<>(edition, HttpStatus.CREATED);
    }

    @DeleteMapping("/edition/{editionId}")
    public void removeEdition(@PathVariable long editionId) throws EditionNotFoundException {
        Optional<Edition> optionalEdition = editionService.getEditionById(editionId);
            if(optionalEdition.isPresent()) {
                editionService.removeEdition(editionId);
            } else {
                throw new EditionNotFoundException(editionId);
            }
    }

    @PutMapping("edition/{editionId}")
    public void modifyEdition(@RequestBody Edition edition, @PathVariable long editionId) throws EditionNotFoundException {
        Optional<Edition> optionalEdition = editionService.getEditionById(editionId);
        if (optionalEdition.isPresent()) {
            editionService.modifyEdition(edition, editionId);
        } else {
            throw new EditionNotFoundException(editionId);
        }
    }

    @ExceptionHandler(EditionNotFoundException.class)
    public ResponseEntity<ErrorResponse> editionNotFoundException(EditionNotFoundException enfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, enfe.getMessage());
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
