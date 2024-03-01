package com.svalero.euroapi.repository;
import com.svalero.euroapi.domain.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SongRepository extends CrudRepository<Song, Long> {
    List<Song> findAll();
    List <Song> findByTitle(String title);
    List <Song> findByTitleAndVotes(String title, int votes);

}
