package com.svalero.euroapi.repository;
import com.svalero.euroapi.domain.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SongRepository extends CrudRepository<Song, Long> {
    List<Song> findAll();
    List <Song> findByTitle(String title);
    List<Song> findByVotes(int votes);
    List <Song> findByWinner(boolean winner);
    List <Song> findByTitleAndVotes(String title, int votes);
    List <Song> findByTitleAndWinner(String title, boolean winner);
    List <Song> findByVotesAndWinner(int votes, boolean winner);

    List <Song> findByTitleAndVotesAndWinner(String title, int votes, boolean winner);

}
