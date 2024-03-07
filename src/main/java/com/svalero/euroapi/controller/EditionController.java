package com.svalero.euroapi.controller;
import com.svalero.euroapi.domain.Edition;
import com.svalero.euroapi.domain.ErrorResponse;
import com.svalero.euroapi.domain.dto.EditionInDto;
import com.svalero.euroapi.domain.dto.EditionOutDto;
import com.svalero.euroapi.exception.EditionNotFoundException;
import com.svalero.euroapi.exception.VenueNotFoundException;
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
    public ResponseEntity<EditionOutDto> getEdition(@PathVariable long editionId){
        try {
            EditionOutDto editionOutDto = editionService.getEditionById(editionId);
            return ResponseEntity.ok(editionOutDto);
        } catch (EditionNotFoundException enfe) {
            throw new RuntimeException(enfe);
        }
    }

    @GetMapping("/editions")
    public ResponseEntity<List<EditionOutDto>> getAll(@RequestParam(defaultValue = "0") Integer editionNum,
                                @RequestParam(defaultValue= "") String countryOrganizer,
                                @RequestParam(defaultValue = "") String cancelled) {
        List<EditionOutDto> editionOutDtoList = editionService.getEditions(editionNum, countryOrganizer,cancelled);
        return ResponseEntity.ok(editionOutDtoList);
    }

    @PostMapping("/editions")
    public ResponseEntity<EditionOutDto> saveEdition (@Valid @RequestBody EditionInDto editionInDto) {
        EditionOutDto editionOutDto = null;
        try {
            editionOutDto = editionService.saveEdition(editionInDto);
        } catch (VenueNotFoundException vnfe) {
            throw new RuntimeException(vnfe);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(editionOutDto);
    }

    @DeleteMapping("/edition/{editionId}")
    public ResponseEntity<Void> removeEdition(@PathVariable long editionId) {
        try {
            editionService.removeEdition(editionId);
            return ResponseEntity.noContent().build();
        } catch (EditionNotFoundException enfe) {
            throw new RuntimeException(enfe);
        }
    }

    @PutMapping("edition/{editionId}")
    public ResponseEntity<EditionOutDto> modifyEdition(@PathVariable long editionId, @Valid @RequestBody EditionInDto editionInDto){
        try {
            EditionOutDto editionOutDto = editionService.modifyEdition(editionId, editionInDto);
            return ResponseEntity.ok(editionOutDto);
        } catch (EditionNotFoundException enfe) {
            throw new RuntimeException(enfe);
        } catch (VenueNotFoundException vnfe) {
            throw new RuntimeException(vnfe);
        }
    }

    @ExceptionHandler(EditionNotFoundException.class)
    public ResponseEntity<ErrorResponse> editionNotFoundException(EditionNotFoundException enfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, enfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
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
