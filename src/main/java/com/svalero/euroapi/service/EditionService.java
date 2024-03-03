package com.svalero.euroapi.service;
import com.svalero.euroapi.domain.Edition;
import com.svalero.euroapi.repository.EditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EditionService {
    @Autowired
    private EditionRepository editionRepository;
    public List<Edition> getEditions() {return editionRepository.findAll(); }
    public Optional<Edition> getEditionById(long id) {return editionRepository.findById(id);}
    public List<Edition> getEditionByEdition(int edition){return editionRepository.findByEdition(edition);}
    public List<Edition> getEditionByCountryOrganizer(String countryOrganizer){return editionRepository.findByCountryOrganizer(countryOrganizer);}
    public List<Edition> getEditionByCancelled(boolean cancelled){return editionRepository.findByCancelled(cancelled);}
    public List<Edition> getEditionByEditionAndCountryOrganizer(int edition, String countryOrganizer){return editionRepository.findByEditionAndCountryOrganizer(edition, countryOrganizer);}
    public List<Edition> getEditionByEditionAndCancelled(int edition, boolean cancelled){return editionRepository.findByEditionAndCancelled(edition,cancelled);}
    public List<Edition> getEditionByCountryOrganizerAndCancelled(String countryOrganizer, boolean cancelled) {return editionRepository.findByCountryOrganizerAndCancelled(countryOrganizer,cancelled);}
    public List<Edition> getEditionByEditionAndCountryOrganizerAndCancelled(int edition, String countryOrganizer, boolean cancelled){return editionRepository.findByEditionAndCountryOrganizerAndCancelled(edition,countryOrganizer,cancelled);}
    public void saveEdition(Edition edition) {editionRepository.save(edition); }

    public void removeEdition(long editionId){editionRepository.deleteById(editionId);}

    public void modifyEdition (Edition newEdition, long editionId) {
        Optional<Edition> edition = editionRepository.findById(editionId);
        if (edition.isPresent()){
            Edition existingEdition = edition.get();
            existingEdition.setEdition(newEdition.getEdition());
            existingEdition.setRomanNumeralEdition(newEdition.getRomanNumeralEdition());
            existingEdition.setCountryOrganizer(newEdition.getCountryOrganizer());
            existingEdition.setFinalDate(newEdition.getFinalDate());
            existingEdition.setSlogan(newEdition.getSlogan());
            existingEdition.setCancelled(newEdition.isCancelled());
            existingEdition.setTotalBudget(newEdition.getTotalBudget());
            editionRepository.save(edition.get());
        }
    }
}
