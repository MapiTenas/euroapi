package com.svalero.euroapi.controller;
import com.svalero.euroapi.domain.Country;
import com.svalero.euroapi.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping("/countries")
    public List<Country> getAll() {return countryService.getCountries(); }

    @PostMapping("/countries")
    public void saveCountry (@RequestBody Country country) {
        countryService.saveCountry(country);
    }

    @DeleteMapping("/country/{countryId}")
    public void removeCountry(@PathVariable long countryId) {
        countryService.removeCountry(countryId);
    }

    @PutMapping("/country/{countryId}")
    public void modifyCountry(@RequestBody Country country, @PathVariable long countryId) {
        countryService.modifyCountry(country, countryId);
    }
}
