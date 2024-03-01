package com.svalero.euroapi.controller;
import com.svalero.euroapi.domain.ErrorResponse;
import com.svalero.euroapi.domain.Song;
import com.svalero.euroapi.exception.SongNotFoundException;
import com.svalero.euroapi.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SongController {

    @Autowired
    private SongService songService;

    @GetMapping("/song/{songId}")
    public Song getSong(@PathVariable long songId) throws SongNotFoundException {
        Optional<Song> optionalSong = songService.getSongById(songId);
        return optionalSong.orElseThrow(() -> new SongNotFoundException(songId));
    }

    @GetMapping("/songs")
    public List<Song> getAll(@RequestParam(defaultValue = "") String title, @RequestParam(defaultValue = "0") int votes) {
        if (!title.isEmpty() && votes == 0) {
            return songService.getSongByTitle(title);
        } else if (!title.isEmpty() && votes != 0) {
            return songService.getSongByTitleAndVotes(title, votes);
        }
        return songService.getSongs();
    }



    @PostMapping("/songs")
    public void saveSong (@RequestBody Song song) {

        songService.saveSong(song);
    }

    @DeleteMapping("/song/{songId}")
    public void removeSong(@PathVariable long songId) {

        songService.removeSong(songId);
    }

    @PutMapping("/song/{songId}")
    public void modifySong(@RequestBody Song song, @PathVariable long songId) {

        songService.modifySong(song, songId);
    }

    @ExceptionHandler(SongNotFoundException.class)
    public ResponseEntity<ErrorResponse> songNotFoundException(SongNotFoundException snfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, snfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
