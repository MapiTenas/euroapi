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
    public List<Song> getAll(@RequestParam(defaultValue = "") String title,
                             @RequestParam(defaultValue = "0") int votes,
                             @RequestParam(defaultValue = "") String winner) {
        if (!title.isEmpty()) {
            if (votes !=0 && !winner.isEmpty()) {
                return songService.getSongByTitleAndVotesAndWinner(title, votes, Boolean.valueOf(winner));
            } else if (votes != 0) {
                return songService.getSongByTitleAndVotes(title, votes);
            } else if (!winner.isEmpty()) {
                return songService.getSongByTitleAndWinner(title, Boolean.valueOf(winner));
            } else {
                return songService.getSongByTitle(title);
            }
        } else if (votes != 0) {
            if (!winner.isEmpty()) {
                return songService.getSongByVotesAndWinner(votes, Boolean.valueOf(winner));
            } else {
                return songService.getSongByVotes(votes);
            }
        } else if (!winner.isEmpty()) {
            return songService.getSongByWinner(Boolean.valueOf(winner));
        } else {
            return songService.getSongs();
        }
    }


    @PostMapping("/songs")
    public void saveSong (@RequestBody Song song) {

        songService.saveSong(song);
    }

    @DeleteMapping("/song/{songId}")
    public void removeSong(@PathVariable long songId) throws SongNotFoundException{
        Optional<Song> optionalSong = songService.getSongById(songId);
            if(optionalSong.isPresent()) {
                songService.removeSong(songId);
            } else {
                throw new SongNotFoundException(songId);
            }
    }

    @PutMapping("/song/{songId}")
    public void modifySong(@RequestBody Song song, @PathVariable long songId) throws SongNotFoundException {
        Optional<Song> optionalSong = songService.getSongById(songId);
        if (optionalSong.isPresent()) {
            songService.modifySong(song, songId);
        } else {
            throw new SongNotFoundException(songId);
        }
    }

    @ExceptionHandler(SongNotFoundException.class)
    public ResponseEntity<ErrorResponse> songNotFoundException(SongNotFoundException snfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, snfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
