package com.svalero.euroapi.controller;
import com.svalero.euroapi.domain.Song;
import com.svalero.euroapi.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SongController {

    @Autowired
    private SongService songService;

    @GetMapping("/songs")
    public List<Song> getAll() {
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

}
