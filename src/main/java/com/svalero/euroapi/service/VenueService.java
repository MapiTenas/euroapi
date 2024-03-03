package com.svalero.euroapi.service;
import com.svalero.euroapi.domain.Venue;
import com.svalero.euroapi.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService {
    @Autowired
    private VenueRepository venueRepository;

    public List<Venue> getVenues() {return venueRepository.findAll(); }
    public Optional<Venue> getVenueById(long id){return venueRepository.findById(id);}
    public List<Venue> getVenueByVenueName(String venueName) {return venueRepository.findByVenueName(venueName);}
    public List<Venue> getVenueByCity(String city) {return venueRepository.findByCity(city);}
    public List<Venue> getVenueByAdapted(boolean adapted){return venueRepository.findByAdapted(adapted);}
    public List<Venue> getVenueByVenueNameAndCity(String venueName, String city) {return venueRepository.findByVenueNameAndCity(venueName,city);}
    public List<Venue> getVenueByVenueNameAndAdapted(String venueName, boolean adapted){return venueRepository.findByVenueNameAndAdapted(venueName, adapted);}
    public List<Venue> getVenueByCityAndAdapted(String city, boolean adapted) {return venueRepository.findByCityAndAdapted(city, adapted);}
    public List<Venue> getVenueByVenueNameAndCityAndAdapted(String venueName, String city, boolean adapted) {return venueRepository.findByVenueNameAndCityAndAdapted(venueName,city,adapted);}
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
            existingVenue.setCity(newVenue.getCity());
            existingVenue.setCapacity(newVenue.getCapacity());
            existingVenue.setFoundationDate(newVenue.getFoundationDate());
            existingVenue.setAdapted(newVenue.isAdapted());
            existingVenue.setLatitude(newVenue.getLatitude());
            existingVenue.setLongitude(newVenue.getLongitude());
            venueRepository.save(venue.get());
        }
    }
}
