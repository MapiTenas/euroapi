package com.svalero.euroapi.service;
import com.svalero.euroapi.domain.ErrorResponse;
import com.svalero.euroapi.domain.Venue;
import com.svalero.euroapi.domain.dto.VenueInDto;
import com.svalero.euroapi.domain.dto.VenueOutDto;
import com.svalero.euroapi.exception.VenueNotFoundException;
import com.svalero.euroapi.repository.VenueRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VenueService {
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<VenueOutDto> getVenues(String venueName, String city, String adapted) {
        List <Venue> venueList;
        if (!venueName.isEmpty()){
            if (!city.isEmpty() && !adapted.isEmpty()) {
                venueList = venueRepository.findByVenueNameAndCityAndAdapted(venueName,city,Boolean.valueOf(adapted));
            } else if (!city.isEmpty()) {
                venueList = venueRepository.findByVenueNameAndCity(venueName,city);
            } else if (!adapted.isEmpty()){
                venueList = venueRepository.findByVenueNameAndAdapted(venueName, Boolean.valueOf(adapted));
            } else {
                venueList = venueRepository.findByVenueName(venueName);
            }
        } else if (!city.isEmpty()) {
            if (!adapted.isEmpty()) {
                venueList = venueRepository.findByCityAndAdapted(city, Boolean.valueOf(adapted));
            } else {
                venueList = venueRepository.findByCity(city);
            }
        } else if (!adapted.isEmpty()){
            venueList = venueRepository.findByAdapted(Boolean.valueOf(adapted));
        } else {
            venueList = venueRepository.findAll();
        }
        List<VenueOutDto> venueOutDtoList = new ArrayList<>();
        for (Venue venue : venueList){
            venueOutDtoList.add(modelMapper.map(venue, VenueOutDto.class));
        }
        return venueOutDtoList;
    }
    public VenueOutDto getVenueById(long id) throws VenueNotFoundException {
        Optional<Venue> venueOptional = venueRepository.findById(id);
        if(venueOptional.isPresent()){
            return modelMapper.map(venueOptional.get(), VenueOutDto.class);
        } else {
            throw new VenueNotFoundException(id);
        }
    }

    public VenueOutDto saveVenue (VenueInDto venueInDto) {
        Venue venue = modelMapper.map(venueInDto, Venue.class);
        return modelMapper.map(venueRepository.save(venue), VenueOutDto.class);
    }


    public void removeVenue (long venueId) throws VenueNotFoundException {
        if(venueRepository.existsById(venueId)) {
            venueRepository.deleteById(venueId);
        } else {
            throw new VenueNotFoundException(venueId);
        }
    }
    public VenueOutDto modifyVenue (long venueId, VenueInDto venueInDto) throws VenueNotFoundException {
        Optional<Venue> venueOptional = venueRepository.findById(venueId);
        if (venueOptional.isPresent()) {
            Venue venue = venueOptional.get();
            modelMapper.map(venueInDto, venue);
            return modelMapper.map(venueRepository.save(venue), VenueOutDto.class);
        } else {
            throw new VenueNotFoundException(venueId);
        }

    }

    @ExceptionHandler(VenueNotFoundException.class)
    public ResponseEntity<ErrorResponse> venueNotFoundException(VenueNotFoundException vnfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, vnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }



}
