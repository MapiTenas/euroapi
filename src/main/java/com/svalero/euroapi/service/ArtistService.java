package com.svalero.euroapi.service;
import com.svalero.euroapi.domain.Artist;
import com.svalero.euroapi.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    public List<Artist> getArtists() {return artistRepository.findAll();}

    public void saveArtist (Artist artist){
        artistRepository.save(artist);
    }

    public void removeArtist (long artistId){
        artistRepository.deleteById(artistId);
    }

    public void modifyArtist(Artist newArtist, long artistId){
        Optional<Artist> artist = artistRepository.findById(artistId);
        if (artist.isPresent()) {
            Artist existingArtist = artist.get();
            existingArtist.setName(newArtist.getName());
            existingArtist.setBirthDate(newArtist.getBirthDate());
            existingArtist.setOriginCountry(newArtist.getOriginCountry());
            existingArtist.setPublishedCds(newArtist.getPublishedCds());
            existingArtist.setActive(newArtist.isActive());
            existingArtist.setIgFollowers(newArtist.getIgFollowers());
            artistRepository.save(artist.get());

        }
    }
}
