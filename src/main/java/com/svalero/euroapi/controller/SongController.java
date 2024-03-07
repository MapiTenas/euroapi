package com.svalero.euroapi.controller;
import com.svalero.euroapi.domain.ErrorResponse;
import com.svalero.euroapi.domain.dto.SongInDto;
import com.svalero.euroapi.domain.dto.SongOutDto;
import com.svalero.euroapi.exception.ArtistNotFoundException;
import com.svalero.euroapi.exception.CountryNotFoundException;
import com.svalero.euroapi.exception.EditionNotFoundException;
import com.svalero.euroapi.exception.SongNotFoundException;
import com.svalero.euroapi.service.SongService;
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
public class SongController {

    @Autowired
    private SongService songService;
    private Logger logger = LoggerFactory.getLogger(SongController.class);


    @GetMapping("/song/{songId}")
    public ResponseEntity<SongOutDto> getSong(@PathVariable long songId){
        try {
            logger.info("ini GET /song/" + songId);
            SongOutDto songOutDto = songService.getSongById(songId);
            logger.info("end GET /song/" + songId);
            return ResponseEntity.ok(songOutDto);
        } catch (SongNotFoundException snfe) {
            throw new RuntimeException(snfe);
        }
    }

    @GetMapping("/songs")
    public ResponseEntity<List<SongOutDto>> getAll(@RequestParam(defaultValue = "") String title,
                             @RequestParam(defaultValue = "0") int votes,
                             @RequestParam(defaultValue = "") String winner) {
        logger.info("ini GET /songs");
        List<SongOutDto> songOutDtoList = songService.getSongs(title, votes,winner);
        logger.info("end GET /songs");
        return ResponseEntity.ok(songOutDtoList);
    }


    @PostMapping("/songs")
    public ResponseEntity<SongOutDto> saveSong (@Valid @RequestBody SongInDto songInDto) {
        SongOutDto songOutDto = null;
        try {
            logger.info("ini POST /songs");
            songOutDto = songService.saveSong(songInDto);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        logger.info("end POST /songs");
        return ResponseEntity.status(HttpStatus.CREATED).body(songOutDto);
    }

    @DeleteMapping("/song/{songId}")
    public ResponseEntity<Void> removeSong(@PathVariable long songId){
       try {
           logger.info("ini DELETE /song/" + songId);
           songService.removeSong(songId);
           logger.info("end DELETE /song/" + songId);
           return ResponseEntity.noContent().build();
       } catch (SongNotFoundException snfe) {
           throw new RuntimeException(snfe);
       }
    }

    @PutMapping("/song/{songId}")
    public ResponseEntity<SongOutDto> modifySong(@PathVariable long songId, @Valid @RequestBody SongInDto songInDto) {
        try {
            logger.info("ini PUT /song/" + songId);
            SongOutDto songOutDto = songService.modifySong(songId, songInDto);
            logger.info("end PUT /song/" + songId);
            return ResponseEntity.ok(songOutDto);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @ExceptionHandler(SongNotFoundException.class)
    public ResponseEntity<ErrorResponse> songNotFoundException(SongNotFoundException snfe) {
        logger.error(snfe.getMessage(), snfe);
        ErrorResponse errorResponse = new ErrorResponse(404, snfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<ErrorResponse> artistNotFoundException(ArtistNotFoundException anfe) {
        logger.error(anfe.getMessage(), anfe);
        ErrorResponse errorResponse = new ErrorResponse(404, anfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ErrorResponse> countryNotFoundException(CountryNotFoundException cnfe) {
        logger.error(cnfe.getMessage(), cnfe);
        ErrorResponse errorResponse = new ErrorResponse(404, cnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EditionNotFoundException.class)
    public ResponseEntity<ErrorResponse> editionNotFoundException(EditionNotFoundException enfe) {
        logger.error(enfe.getMessage(), enfe);
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
        logger.error(ex.getMessage(), ex);
        return errors;
    }


}
