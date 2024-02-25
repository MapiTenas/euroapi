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
    public List<Artist> getAll() {return artistService.getArtists();}

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
