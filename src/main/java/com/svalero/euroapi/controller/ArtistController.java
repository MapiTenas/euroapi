package com.svalero.euroapi.controller;
import com.svalero.euroapi.domain.Artist;
import com.svalero.euroapi.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArtistController {

    @Autowired
    private ArtistService artistService;

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
    public void removeArtist(@PathVariable long artistId) {
        artistService.removeArtist(artistId);
    }
    @PutMapping("/artist/{artistId}")
    public void modifyArtist(@RequestBody Artist artist, @PathVariable long artistId) {
        artistService.modifyArtist(artist, artistId);
    }
}
