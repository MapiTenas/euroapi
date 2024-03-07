package com.svalero.euroapi.controller;
import com.svalero.euroapi.domain.ErrorResponse;
import com.svalero.euroapi.domain.dto.ArtistInDto;
import com.svalero.euroapi.domain.dto.ArtistOutDto;
import com.svalero.euroapi.exception.ArtistNotFoundException;
import com.svalero.euroapi.service.ArtistService;
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
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    private Logger logger = LoggerFactory.getLogger(ArtistController.class);


    @GetMapping("/artist/{artistId}")
    public ResponseEntity<ArtistOutDto> getArtist(@PathVariable long artistId) {
        try {
            logger.info("ini GET /artist/" + artistId);
            ArtistOutDto artistOutDto = artistService.getArtistById(artistId);
            logger.info("end GET /artist/"+ artistId);
            return ResponseEntity.ok(artistOutDto);
        } catch (ArtistNotFoundException anfe) {
            throw new RuntimeException(anfe);
        }
    }

    @GetMapping("/artists")
    public ResponseEntity<List<ArtistOutDto>> getArtists(@RequestParam(defaultValue = "") String name,
                                                         @RequestParam(defaultValue = "") String originCountry,
                                                         @RequestParam(defaultValue = "") String active) {
        logger.info("ini GET /artists");
        List<ArtistOutDto> artistOutDtoList = artistService.getArtists(name, originCountry,active);
        logger.info("end GET /artists" );
        return ResponseEntity.ok(artistOutDtoList);
    }

    @PostMapping("/artists")
    public ResponseEntity<ArtistOutDto> saveArtist(@Valid @RequestBody ArtistInDto artistInDto) {
        logger.info("ini POST /artists");
        ArtistOutDto artistOutDto = artistService.saveArtist(artistInDto);
        logger.info("end POST /artists");
        return ResponseEntity.status(HttpStatus.CREATED).body(artistOutDto);
    }

    @DeleteMapping("/artist/{artistId}")
    public ResponseEntity<Void> removeArtist(@PathVariable long artistId) {
        try {
            logger.info("ini DELETE /artist/" + artistId);
            artistService.removeArtist(artistId);
            logger.info("end DELETE /artist/" + artistId);
            return ResponseEntity.noContent().build();
        } catch (ArtistNotFoundException anfe) {
            throw new RuntimeException(anfe);
        }
    }

    @PutMapping("/artist/{artistId}")
    public ResponseEntity<ArtistOutDto> modifyArtist(@PathVariable long artistId, @Valid @RequestBody ArtistInDto artistInDto){
        try {
            logger.info("ini PUT /artist/" + artistId);
            ArtistOutDto artistOutDto = artistService.modifyArtist(artistId, artistInDto);
            logger.info("end PUT /artist/" + artistId);
            return ResponseEntity.ok(artistOutDto);
        } catch (ArtistNotFoundException anfe) {
            throw new RuntimeException(anfe);
        }
    }
    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<ErrorResponse> artistNotFoundException(ArtistNotFoundException anfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, anfe.getMessage());
        logger.error(anfe.getMessage(), anfe);
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