package com.svalero.euroapi.domain.dto;
import com.svalero.euroapi.domain.Artist;
import com.svalero.euroapi.domain.Country;
import com.svalero.euroapi.domain.Edition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongOutDto {
    private long id;
    private String title;
    private float duration;
    private String language;
    private int votes;
    private boolean winner;
    private LocalDate admissionDate;
    private Artist artist;
    private Country country;
    private Edition edition;
}
