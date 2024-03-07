package com.svalero.euroapi.service;
import com.svalero.euroapi.domain.Edition;
import com.svalero.euroapi.domain.ErrorResponse;
import com.svalero.euroapi.domain.Venue;
import com.svalero.euroapi.domain.dto.EditionInDto;
import com.svalero.euroapi.domain.dto.EditionOutDto;
import com.svalero.euroapi.exception.EditionNotFoundException;
import com.svalero.euroapi.exception.VenueNotFoundException;
import com.svalero.euroapi.repository.EditionRepository;
import com.svalero.euroapi.repository.VenueRepository;
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
public class EditionService {
    @Autowired
    private EditionRepository editionRepository;
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<EditionOutDto> getEditions(Integer editionNum, String countryOrganizer,String cancelled) {
        List<Edition> editionList;
        if (editionNum != 0){
            if (!countryOrganizer.isEmpty() && !cancelled.isEmpty()){
                editionList = editionRepository.findByEditionNumAndCountryOrganizerAndCancelled(editionNum, countryOrganizer,Boolean.valueOf(cancelled));
            } else if (!countryOrganizer.isEmpty()) {
                editionList = editionRepository.findByEditionNumAndCountryOrganizer(editionNum,countryOrganizer);
            } else if (!cancelled.isEmpty()) {
                editionList = editionRepository.findByEditionNumAndCancelled(editionNum, Boolean.valueOf(cancelled));
            } else {
                editionList = editionRepository.findByEditionNum(editionNum);
            }
        } else if (!countryOrganizer.isEmpty()) {
            if (!cancelled.isEmpty()){
                editionList = editionRepository.findByCountryOrganizerAndCancelled(countryOrganizer, Boolean.valueOf(cancelled));
            } else {
                editionList = editionRepository.findByCountryOrganizer(countryOrganizer);
            }
        } else if (!cancelled.isEmpty()){
            editionList = editionRepository.findByCancelled(Boolean.valueOf(cancelled));
        } else {
            editionList = editionRepository.findAll();
        }

        List<EditionOutDto> editionOutDtoList = new ArrayList<>();
        for (Edition editionItem : editionList) {
            editionOutDtoList.add(modelMapper.map(editionItem, EditionOutDto.class));
        }
        return editionOutDtoList;
    }
    public EditionOutDto getEditionById(long id) throws EditionNotFoundException{
        Optional<Edition> editionOptional = editionRepository.findById(id);
        if(editionOptional.isPresent()) {
            return modelMapper.map(editionOptional.get(), EditionOutDto.class);
        } else {
            throw new EditionNotFoundException(id);
        }
    }
    public EditionOutDto saveEdition(EditionInDto editionInDto) throws VenueNotFoundException {
        Edition edition = modelMapper.map(editionInDto, Edition.class);
        Long venueId = editionInDto.getVenueId();

        Optional<Venue> venueOptional = venueRepository.findById(venueId);
        if(venueOptional.isPresent()){
            edition.setVenue(venueOptional.get());
        } else {
            throw new VenueNotFoundException(venueId);
        }
        return modelMapper.map(editionRepository.save(edition), EditionOutDto.class);
    }

    public void removeEdition(long editionId) throws EditionNotFoundException{
        if(editionRepository.existsById(editionId)) {
            editionRepository.deleteById(editionId);
        } else {
            throw new EditionNotFoundException(editionId);
        }
    }

    public EditionOutDto modifyEdition (long editionId, EditionInDto editionInDto) throws EditionNotFoundException, VenueNotFoundException {
        Optional<Edition> editionOptional = editionRepository.findById(editionId);
        if (editionOptional.isPresent()){
            Edition edition = editionOptional.get();
            modelMapper.map(editionInDto, edition);
            Long venueId = editionInDto.getVenueId();
            Optional<Venue> venueOptional = venueRepository.findById(venueId);
            if (venueOptional.isPresent()) {
                edition.setVenue(venueOptional.get());
            } else {
                throw new VenueNotFoundException(venueId);
            }
            return modelMapper.map(editionRepository.save(edition), EditionOutDto.class);
        } else {
            throw new EditionNotFoundException(editionId);
        }
    }
    @ExceptionHandler(EditionNotFoundException.class)
    public ResponseEntity<ErrorResponse> editionNotFoundException(EditionNotFoundException enfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, enfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VenueNotFoundException.class)
    public ResponseEntity<ErrorResponse> venueNotFoundException(VenueNotFoundException vnfe) {
        ErrorResponse errorResponse = new ErrorResponse(404, vnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
