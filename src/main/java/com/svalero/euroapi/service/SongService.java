package com.svalero.euroapi.service;
import com.svalero.euroapi.domain.Song;
import com.svalero.euroapi.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    public List<Song> getSongs(){
        return songRepository.findAll();
    }

    public Optional<Song> getSongById(long id){
        return songRepository.findById(id);
    }

    public Song getSongByTitle(String title){
        return songRepository.findByTitle(title);
    }

    public void saveSong(Song song) {

        songRepository.save(song);
    }

    public void removeSong (long songId) {

        songRepository.deleteById(songId);
    }

    public void modifySong (Song newSong, long songId) {
        Optional<Song> song = songRepository.findById(songId);
        if (song.isPresent()) {
            Song existingSong = song.get();
            existingSong.setTitle(newSong.getTitle());
            existingSong.setDuration(newSong.getDuration());
            existingSong.setLanguage(newSong.getLanguage());
            existingSong.setVotes(newSong.getVotes());
            existingSong.setWinner(newSong.isWinner());
            existingSong.setAdmissionDate(newSong.getAdmissionDate());
            songRepository.save(song.get());
        }
    }
}
