package com.svalero.euroapi.service;
import com.svalero.euroapi.domain.Country;
import com.svalero.euroapi.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    public List<Country> getCountries() {return countryRepository.findAll(); }

    public void saveCountry(Country country) {
        countryRepository.save(country);
    }

    public void removeCountry(long countryId) {
        countryRepository.deleteById(countryId);
    }

    public void modifyCountry (Country newCountry, long countryId) {
    Optional<Country> country = countryRepository.findById(countryId);
    if (country.isPresent()){
        Country existingCountry = country.get();
        existingCountry.setCountryName(newCountry.getCountryName());
        existingCountry.setBigFive(newCountry.isBigFive());
        existingCountry.setEditionsWinned(newCountry.getEditionsWinned());
        existingCountry.setPublicBroadcaster(newCountry.getPublicBroadcaster());
        existingCountry.setParticipationFee(newCountry.getParticipationFee());
        existingCountry.setJoiningDate(newCountry.getJoiningDate());
        countryRepository.save(country.get());
        }
    }

}
