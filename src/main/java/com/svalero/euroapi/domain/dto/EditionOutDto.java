package com.svalero.euroapi.domain.dto;
import com.svalero.euroapi.domain.Song;
import com.svalero.euroapi.domain.Venue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditionOutDto {
    private long id;
    private Integer editionNum;
    private String romanNumeralEdition;
    private String countryOrganizer;
    private LocalDate finalDate;
    private String slogan;
    private boolean cancelled;
    private Venue venue;
    private List<Song> songs;
}
