package com.svalero.euroapi.repository;
import com.svalero.euroapi.domain.Artist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends CrudRepository<Artist, Long> {
    List<Artist> findAll();
}
