package com.svalero.euroapi.repository;
import com.svalero.euroapi.domain.Edition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EditionRepository extends CrudRepository<Edition, Long> {
    List<Edition> findAll();
    List<Edition> findByEditionNum (Integer editionNum);
    List<Edition> findByCountryOrganizer (String countryOrganizer);
    List<Edition> findByCancelled (boolean cancelled);
    List <Edition> findByEditionNumAndCountryOrganizer (Integer editionNum, String countryOrganizer);
    List <Edition> findByEditionNumAndCancelled (Integer editionNum, boolean cancelled);
    List <Edition> findByCountryOrganizerAndCancelled(String countryOrganizer, boolean cancelled);
    List <Edition> findByEditionNumAndCountryOrganizerAndCancelled (Integer editionNum, String countryOrganizer, boolean cancelled);

}
