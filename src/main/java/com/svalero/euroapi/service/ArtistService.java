package com.svalero.euroapi.service;
import com.svalero.euroapi.domain.Artist;
import com.svalero.euroapi.domain.ErrorResponse;
import com.svalero.euroapi.domain.dto.ArtistInDto;
import com.svalero.euroapi.domain.dto.ArtistOutDto;
import com.svalero.euroapi.exception.ArtistNotFoundException;
import com.svalero.euroapi.repository.ArtistRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<ArtistOutDto> getArtists(String name, String originCountry, String active) {
        List<Artist> artistList;
        if (!name.isEmpty()) {
            if (!originCountry.isEmpty() && !active.isEmpty()) {
                artistList = artistRepository.findByNameAndOriginCountryAndActive(name, originCountry, Boolean.valueOf(active));
            } else if (!originCountry.isEmpty()) {
                artistList = artistRepository.findByNameAndOriginCountry(name, originCountry);
            } else if (!active.isEmpty()) {
                artistList = artistRepository.findByNameAndActive(name, Boolean.valueOf(active));
            } else {
                artistList = artistRepository.findByName(name);
            }
        } else if (!originCountry.isEmpty()) {
            if (!active.isEmpty()) {
                artistList = artistRepository.findByOriginCountryAndActive(originCountry, Boolean.valueOf(active));
            } else {
                artistList = artistRepository.findByOriginCountry(originCountry);
            }
        } else if (!active.isEmpty()) {
            artistList = artistRepository.findByActive(Boolean.valueOf(active));
        } else {
            artistList = artistRepository.findAll();
        }

        List<ArtistOutDto> artistOutDtoList = new ArrayList<>();
        for (Artist artist : artistList) {
            artistOutDtoList.add(modelMapper.map(artist, ArtistOutDto.class));
        }
        return artistOutDtoList;
    }

    public ArtistOutDto getArtistById(long id) throws ArtistNotFoundException {
        Optional<Artist> artistOptional = artistRepository.findById(id);
        if(artistOptional.isPresent()) {
            return modelMapper.map(artistOptional.get(), ArtistOutDto.class);
        } else {
            throw new ArtistNotFoundException(id);
        }
    }

    public ArtistOutDto saveArtist (ArtistInDto artistInDto) {
        Artist artist = modelMapper.map(artistInDto, Artist.class);
        return modelMapper.map(artistRepository.save(artist),ArtistOutDto.class);
    }

    public void removeArtist (long artistId) throws ArtistNotFoundException{
        if(artistRepository.existsById(artistId)) {
            artistRepository.deleteById(artistId);
        } else {
            throw new ArtistNotFoundException(artistId);
        }
    }

    public ArtistOutDto modifyArtist(long artistId, ArtistInDto artistInDto) throws ArtistNotFoundException{
        Optional<Artist> artistOptional = artistRepository.findById(artistId);
        if (artistOptional.isPresent()) {
            Artist artist = artistOptional.get();
            modelMapper.map(artistInDto, artist);
            return modelMapper.map(artistRepository.save(artist), ArtistOutDto.class);
        } else {
            throw new ArtistNotFoundException(artistId);
        }
    }

    @ExceptionHandler(ArtistNotFoundException.class)
    public ResponseEntity<ErrorResponse> artistNotFoundException(ArtistNotFoundException anfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, anfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
