package com.svalero.euroapi.controller;
import com.svalero.euroapi.domain.Edition;
import com.svalero.euroapi.service.EditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EditionController {
    @Autowired
    private EditionService editionService;

    @GetMapping("/editions")
    public List<Edition> getAll(@RequestParam(defaultValue = "0") int edition,
                                @RequestParam(defaultValue= "") String countryOrganizer,
                                @RequestParam(defaultValue = "") String cancelled) {
        if (edition != 0){
            if (!countryOrganizer.isEmpty() && !cancelled.isEmpty()){
                return editionService.getEditionByEditionAndCountryOrganizerAndCancelled(edition, countryOrganizer,Boolean.valueOf(cancelled));
            } else if (!countryOrganizer.isEmpty()) {
                return editionService.getEditionByEditionAndCountryOrganizer(edition,countryOrganizer);
            } else if (!cancelled.isEmpty()) {
                return editionService.getEditionByEditionAndCancelled(edition, Boolean.valueOf(cancelled));
            } else {
                return editionService.getEditionByEdition(edition);
            }
        } else if (!countryOrganizer.isEmpty()) {
            if (!cancelled.isEmpty()){
                return editionService.getEditionByCountryOrganizerAndCancelled(countryOrganizer, Boolean.valueOf(cancelled));
            } else {
                return editionService.getEditionByCountryOrganizer(countryOrganizer);
            }
        } else if (!cancelled.isEmpty()){
            return editionService.getEditionByCancelled(Boolean.valueOf(cancelled));
        } else {
            return editionService.getEditions();
        }
    }

    @PostMapping("/editions")
    public void saveEdition (@RequestBody Edition edition) {editionService.saveEdition(edition);}

    @DeleteMapping("/edition/{editionId}")
    public void removeEdition(@PathVariable long editionId){editionService.removeEdition(editionId);}

    @PutMapping("edition/{editionId}")
    public void modifyEdition(@RequestBody Edition edition, @PathVariable long editionId) {
        editionService.modifyEdition(edition, editionId);
    }

}
