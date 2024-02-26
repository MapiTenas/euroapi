package com.svalero.euroapi.repository;
import com.svalero.euroapi.domain.Venue;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface VenueRepository extends CrudRepository<Venue, Long> {
    List<Venue> findAll();
}
