package com.svalero.euroapi.service;
import com.svalero.euroapi.domain.Country;
import com.svalero.euroapi.domain.ErrorResponse;
import com.svalero.euroapi.domain.dto.CountryInDto;
import com.svalero.euroapi.domain.dto.CountryOutDto;
import com.svalero.euroapi.exception.CountryNotFoundException;
import com.svalero.euroapi.repository.CountryRepository;
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
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ModelMapper modelMapper;
    public List<CountryOutDto> getCountries(String countryName, String bigFive, int editionsWinned) {
        List<Country> countryList;
        if (!countryName.isEmpty()){
            if(!bigFive.isEmpty() && editionsWinned !=0){
                countryList = countryRepository.findByCountryNameAndBigFiveAndEditionsWinned(countryName,Boolean.valueOf(bigFive),editionsWinned);
            }else if(!bigFive.isEmpty()){
                countryList = countryRepository.findByCountryNameAndBigFive(countryName, Boolean.valueOf(bigFive));
            }else if (editionsWinned !=0) {
                countryList = countryRepository.findByCountryNameAndEditionsWinned(countryName, editionsWinned);
            } else {
                countryList = countryRepository.findByCountryName(countryName);
            }
        } else if (!bigFive.isEmpty()) {
            if (editionsWinned !=0) {
                countryList = countryRepository.findByBigFiveAndEditionsWinned(Boolean.valueOf(bigFive), editionsWinned);
            } else {
                countryList = countryRepository.findByBigFive(Boolean.valueOf(bigFive));
            }
        } else if (editionsWinned != 0) {
            countryList = countryRepository.findByEditionsWinned(editionsWinned);
        } else {
            countryList = countryRepository.findAll();
        }

        List <CountryOutDto> countryOutDtoList = new ArrayList<>();
        for (Country country: countryList) {
            countryOutDtoList.add(modelMapper.map(country, CountryOutDto.class));
        }
        return countryOutDtoList;
    }
    public CountryOutDto getCountryById(long id) throws CountryNotFoundException {
        Optional<Country> countryOptional = countryRepository.findById(id);
        if(countryOptional.isPresent()){
            return modelMapper.map(countryOptional.get(), CountryOutDto.class);
        } else {
            throw new CountryNotFoundException(id);
        }
    }

    public CountryOutDto saveCountry(CountryInDto countryInDto) {
        Country country = modelMapper.map(countryInDto, Country.class);
        return modelMapper.map(countryRepository.save(country), CountryOutDto.class);
    }

    public void removeCountry(long countryId) throws CountryNotFoundException{
        if(countryRepository.existsById(countryId)) {
            countryRepository.deleteById(countryId);
        } else {
            throw new CountryNotFoundException(countryId);
        }
    }

    public CountryOutDto modifyCountry (long countryId, CountryInDto countryInDto) throws CountryNotFoundException {
        Optional<Country> countryOptional = countryRepository.findById(countryId);
        if (countryOptional.isPresent()) {
            Country country = countryOptional.get();
            modelMapper.map(countryInDto, country);
            return modelMapper.map(countryRepository.save(country), CountryOutDto.class);
        } else {
            throw new CountryNotFoundException(countryId);
        }
    }

        @ExceptionHandler(CountryNotFoundException.class)
        public ResponseEntity<ErrorResponse> countryNotFoundException(CountryNotFoundException cnfe) {
            ErrorResponse errorResponse = new ErrorResponse(404, cnfe.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

}
