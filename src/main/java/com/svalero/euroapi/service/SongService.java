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

    public List<Song> getSongByTitle(String title){
        return songRepository.findByTitle(title);
    }

    public List <Song> getSongByVotes(int votes){
        return songRepository.findByVotes(votes);
    }

    public List <Song> getSongByWinner(boolean winner) {return songRepository.findByWinner(winner);}
    public List<Song> getSongByTitleAndVotes(String title, int votes) {
        return songRepository.findByTitleAndVotes(title, votes);
    }

    public List <Song> getSongByTitleAndWinner(String title, boolean winner) {
        return songRepository.findByTitleAndWinner(title, winner);
    }

    public List <Song> getSongByVotesAndWinner(int votes, boolean winner) {
        return songRepository.findByVotesAndWinner(votes, winner);
    }

    public List <Song> getSongByTitleAndVotesAndWinner(String title, int votes, boolean winner) {
        return songRepository.findByTitleAndVotesAndWinner(title, votes, winner);
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
