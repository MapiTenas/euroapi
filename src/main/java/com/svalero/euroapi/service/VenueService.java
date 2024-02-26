package com.svalero.euroapi.service;
import com.svalero.euroapi.domain.Venue;
import com.svalero.euroapi.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VenueService {
    @Autowired
    private VenueRepository venueRepository;

    public List<Venue> getVenues() {return venueRepository.findAll(); }

    public void saveVenue (Venue venue) {
        venueRepository.save(venue);
    }

    public void removeVenue (long venueId) {
        venueRepository.deleteById(venueId);
    }

    public void modifyVenue (Venue newVenue, long venueId) {
        Optional<Venue> venue = venueRepository.findById(venueId);
        if (venue.isPresent()) {
            Venue existingVenue = venue.get();
            existingVenue.setVenueName(newVenue.getVenueName());
            existingVenue.setCapacity(newVenue.getCapacity());
            existingVenue.setFoundationDate(newVenue.getFoundationDate());
            existingVenue.setAdapted(newVenue.isAdapted());
            existingVenue.setLatitude(newVenue.getLatitude());
            existingVenue.setLongitude(newVenue.getLongitude());
            venueRepository.save(venue.get());
        }
    }
}
