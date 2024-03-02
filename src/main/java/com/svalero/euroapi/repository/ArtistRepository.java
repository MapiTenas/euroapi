package com.svalero.euroapi.repository;
import com.svalero.euroapi.domain.Artist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends CrudRepository<Artist, Long> {
    List<Artist> findAll();
    List <Artist> findByName (String name);
    List<Artist> findByOriginCountry(String originCountry);
    List<Artist> findByActive(boolean active);
    List <Artist> findByNameAndOriginCountry(String name, String originCountry);
    List <Artist> findByNameAndActive(String name, boolean active);
    List <Artist> findByOriginCountryAndActive(String originCountry, boolean active);
    List <Artist> findByNameAndOriginCountryAndActive(String name, String originCountry, boolean active);


}
