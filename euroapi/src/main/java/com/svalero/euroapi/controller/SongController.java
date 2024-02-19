package com.svalero.euroapi.controller;
import com.svalero.euroapi.domain.Song;
import com.svalero.euroapi.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
}
