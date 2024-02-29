package com.svalero.euroapi.repository;
import com.svalero.euroapi.domain.Venue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VenueRepository extends CrudRepository<Venue, Long> {
    List<Venue> findAll();
}
