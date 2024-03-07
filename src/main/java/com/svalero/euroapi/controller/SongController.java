package com.svalero.euroapi.controller;
import com.svalero.euroapi.domain.ErrorResponse;
import com.svalero.euroapi.domain.Song;
import com.svalero.euroapi.domain.dto.SongInDto;
import com.svalero.euroapi.domain.dto.SongOutDto;
import com.svalero.euroapi.exception.ArtistNotFoundException;
import com.svalero.euroapi.exception.CountryNotFoundException;
import com.svalero.euroapi.exception.EditionNotFoundException;
import com.svalero.euroapi.exception.SongNotFoundException;
import com.svalero.euroapi.service.SongService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class SongController {

    @Autowired
    private SongService songService;

    @GetMapping("/song/{songId}")
    public ResponseEntity<SongOutDto> getSong(@PathVariable long songId){
        try {
            SongOutDto songOutDto = songService.getSongById(songId);
            return ResponseEntity.ok(songOutDto);
        } catch (SongNotFoundException snfe) {
            throw new RuntimeException(snfe);
        }
    }

    @GetMapping("/songs")
    public ResponseEntity<List<SongOutDto>> getAll(@RequestParam(defaultValue = "") String title,
                             @RequestParam(defaultValue = "0") int votes,
                             @RequestParam(defaultValue = "") String winner) {
        List<SongOutDto> songOutDtoList = songService.getSongs(title, votes,winner);
        return ResponseEntity.ok(songOutDtoList);
    }


    @PostMapping("/songs")
    public ResponseEntity<SongOutDto> saveSong (@Valid @RequestBody SongInDto songInDto) {
        SongOutDto songOutDto = null;
        try {
            songOutDto = songService.saveSong(songInDto);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(songOutDto);
    }

    @DeleteMapping("/song/{songId}")
    public ResponseEntity<Void> removeSong(@PathVariable long songId){
       try {
           songService.removeSong(songId);
           return ResponseEntity.noContent().build();
       } catch (SongNotFoundException snfe) {
           throw new RuntimeException(snfe);
       }
    }

    @PutMapping("/song/{songId}")
    public ResponseEntity<SongOutDto> modifySong(@PathVariable long songId, @Valid @RequestBody SongInDto songInDto) {
        try {
            SongOutDto songOutDto = songService.modifySong(songId, songInDto);
            return ResponseEntity.ok(songOutDto);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @ExceptionHandler(SongNotFoundException.class)
    public ResponseEntity<ErrorResponse> songNotFoundException(SongNotFoundException snfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, snfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<ErrorResponse> artistNotFoundException(ArtistNotFoundException anfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, anfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ErrorResponse> countryNotFoundException(CountryNotFoundException cnfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, cnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
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
