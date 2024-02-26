package com.svalero.euroapi.service;
import com.svalero.euroapi.domain.Country;
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

    public void saveEdition(Edition edition) {editionRepository.save(edition); }

    public void removeEdition(long editionId){editionRepository.deleteById(editionId);}

    public void modifyEdition (Edition newEdition, long editionId) {
        Optional<Edition> edition = editionRepository.findById(editionId);
        if (edition.isPresent()){
            Edition existingEdition = edition.get();
            existingEdition.setEdition(newEdition.getEdition());
            existingEdition.setRomanNumeralEdition(newEdition.getRomanNumeralEdition());
            existingEdition.setFinalDate(newEdition.getFinalDate());
            existingEdition.setSlogan(newEdition.getSlogan());
            existingEdition.setCancelled(newEdition.isCancelled());
            existingEdition.setTotalBudget(newEdition.getTotalBudget());
            editionRepository.save(edition.get());
        }
    }
}
