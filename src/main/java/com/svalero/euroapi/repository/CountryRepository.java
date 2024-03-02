package com.svalero.euroapi.repository;
import com.svalero.euroapi.domain.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {
    List<Country> findAll();
    List<Country> findByCountryName(String countryName);
    List<Country> findByBigFive(boolean bigFive);
    List <Country> findByEditionsWinned(int editionsWinned);
    List <Country> findByCountryNameAndBigFive(String countryName, boolean bigFive);
    List<Country> findByCountryNameAndEditionsWinned (String countryName, int editionsWinned);
    List<Country> findByBigFiveAndEditionsWinned(boolean bigFive, int editionsWinned);
    List<Country> findByCountryNameAndBigFiveAndEditionsWinned(String countryName, boolean bigFive, int editionsWinned);

}
