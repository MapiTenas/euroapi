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
    public List<Edition> getAll() {return editionService.getEditions();}

    @PostMapping("/editions")
    public void saveEdition (@RequestBody Edition edition) {editionService.saveEdition(edition);}

    @DeleteMapping("/edition/{editionId}")
    public void removeEdition(@PathVariable long editionId){editionService.removeEdition(editionId);}

    @PutMapping("edition/{editionId}")
    public void modifyEdition(@RequestBody Edition edition, @PathVariable long editionId) {
        editionService.modifyEdition(edition, editionId);
    }

}
