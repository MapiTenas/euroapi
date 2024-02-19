package com.svalero.euroapi.service;
import com.svalero.euroapi.domain.Song;
import com.svalero.euroapi.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    public List<Song> getSongs(){
        return songRepository.findAll();
    }

    public void saveSong(Song song) {
        songRepository.save(song);
    }
}
