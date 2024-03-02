package com.svalero.euroapi.repository;
import com.svalero.euroapi.domain.Edition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EditionRepository extends CrudRepository<Edition, Long> {
    List<Edition> findAll();
    List<Edition> findByEdition (int edition);
    List<Edition> findByCountryOrganizer (String countryOrganizer);
    List<Edition> findByCancelled (boolean cancelled);
    List <Edition> findByEditionAndCountryOrganizer (int edition, String countryOrganizer);
    List <Edition> findByEditionAndCancelled (int edition, boolean cancelled);
    List <Edition> findByCountryOrganizerAndCancelled(String countryOrganizer, boolean cancelled);
    List <Edition> findByEditionAndCountryOrganizerAndCancelled (int edition, String countryOrganizer, boolean cancelled);

}
