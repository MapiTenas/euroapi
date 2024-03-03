package com.svalero.euroapi.controller;
import com.svalero.euroapi.domain.Artist;
import com.svalero.euroapi.domain.ErrorResponse;
import com.svalero.euroapi.exception.ArtistNotFoundException;
import com.svalero.euroapi.exception.SongNotFoundException;
import com.svalero.euroapi.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @GetMapping("/artist/{artistId}")
    public Artist getArtist(@PathVariable long artistId) throws ArtistNotFoundException {
        Optional<Artist> optionalArtist = artistService.getArtistById(artistId);
        return optionalArtist.orElseThrow(() -> new ArtistNotFoundException(artistId));
    }


    @GetMapping("/artists")
    public List<Artist> getAll(@RequestParam(defaultValue = "") String name,
                               @RequestParam(defaultValue = "") String originCountry,
                               @RequestParam(defaultValue = "") String active) {
        if (!name.isEmpty()) {
            if (!originCountry.isEmpty() && !active.isEmpty()) {
                return artistService.getArtistByNameAndOriginCountryAndActive(name, originCountry, Boolean.valueOf(active));
            } else if (!originCountry.isEmpty()) {
                return artistService.getArtistByNameAndOriginCountry(name, originCountry);
            } else if (!active.isEmpty()) {
                return artistService.getArtistByNameAndActive(name, Boolean.valueOf(active));
            } else {
                return artistService.getArtistbyName(name);
            }
        } else if (!originCountry.isEmpty()) {
            if (!active.isEmpty()) {
                return artistService.getArtistByOriginCountryAndActive(originCountry, Boolean.valueOf(active));
            } else {
                return artistService.getArtistByOriginCountry(originCountry);
            }
        } else if (!active.isEmpty()) {
            return artistService.getArtistByActive(Boolean.valueOf(active));
        } else {
            return artistService.getArtists();
        }
    }

    @PostMapping("/artists")
    public void saveArtist (@RequestBody Artist artist) {
        artistService.saveArtist(artist);
    }
    @DeleteMapping("/artist/{artistId}")
    public void removeArtist(@PathVariable long artistId) throws ArtistNotFoundException {
        Optional<Artist> optionalArtist = artistService.getArtistById(artistId);
            if(optionalArtist.isPresent()) {
                artistService.removeArtist(artistId);
            } else {
                throw new ArtistNotFoundException(artistId);
            }
    }
    @PutMapping("/artist/{artistId}")
    public void modifyArtist(@RequestBody Artist artist, @PathVariable long artistId) throws ArtistNotFoundException {
        Optional<Artist> optionalArtist = artistService.getArtistById(artistId);
        if (optionalArtist.isPresent()) {
            artistService.modifyArtist(artist, artistId);
        } else {
            throw new ArtistNotFoundException(artistId);
        }
    }
    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<ErrorResponse> artistNotFoundException(ArtistNotFoundException anfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, anfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
