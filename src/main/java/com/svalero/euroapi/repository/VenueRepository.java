package com.svalero.euroapi.repository;
import com.svalero.euroapi.domain.Venue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VenueRepository extends CrudRepository<Venue, Long> {
    List<Venue> findAll();
    List<Venue> findByVenueName(String venueName);
    List<Venue> findByCity(String city);
    List<Venue> findByAdapted(boolean adapted);
    List<Venue> findByVenueNameAndCity(String venueName, String city);
    List<Venue> findByVenueNameAndAdapted(String venueName, boolean adapted);
    List<Venue> findByCityAndAdapted(String city, boolean adapted);
    List <Venue> findByVenueNameAndCityAndAdapted(String venueName,String city, boolean adapted);
}
