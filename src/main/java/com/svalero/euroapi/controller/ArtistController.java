package com.svalero.euroapi.controller;
import com.svalero.euroapi.domain.ErrorResponse;
import com.svalero.euroapi.domain.dto.ArtistInDto;
import com.svalero.euroapi.domain.dto.ArtistOutDto;
import com.svalero.euroapi.exception.ArtistNotFoundException;
import com.svalero.euroapi.service.ArtistService;
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

@RestController
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<ArtistOutDto> getArtist(@PathVariable long artistId) {
        try {
            ArtistOutDto artistOutDto = artistService.getArtistById(artistId);
            return ResponseEntity.ok(artistOutDto);
        } catch (ArtistNotFoundException anfe) {
            //Guardar log excepción
            throw new RuntimeException(anfe);
        }
    }

    @GetMapping("/artists")
    public ResponseEntity<List<ArtistOutDto>> getArtists(@RequestParam(defaultValue = "") String name,
                                                         @RequestParam(defaultValue = "") String originCountry,
                                                         @RequestParam(defaultValue = "") String active) {
        List<ArtistOutDto> artistOutDtoList = artistService.getArtists(name, originCountry,active);
        return ResponseEntity.ok(artistOutDtoList);
    }

    @PostMapping("/artists")
    public ResponseEntity<ArtistOutDto> saveArtist(@Valid @RequestBody ArtistInDto artistInDto) {
        ArtistOutDto artistOutDto = artistService.saveArtist(artistInDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(artistOutDto);
    }

    @DeleteMapping("/artist/{artistId}")
    public ResponseEntity<Void> removeArtist(@PathVariable long artistId) {
        try {
            artistService.removeArtist(artistId);
            return ResponseEntity.noContent().build();
        } catch (ArtistNotFoundException anfe) {
            throw new RuntimeException(anfe);
        }
    }

    @PutMapping("/artist/{artistId}")
    public ResponseEntity<ArtistOutDto> modifyArtist(@PathVariable long artistId, @Valid @RequestBody ArtistInDto artistInDto){
        try {
            ArtistOutDto artistOutDto = artistService.modifyArtist(artistId, artistInDto);
            return ResponseEntity.ok(artistOutDto);
        } catch (ArtistNotFoundException anfe) {
            throw new RuntimeException(anfe);
        }
    }
    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<ErrorResponse> artistNotFoundException(ArtistNotFoundException anfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, anfe.getMessage());
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