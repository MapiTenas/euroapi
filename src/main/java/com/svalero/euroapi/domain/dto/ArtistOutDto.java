package com.svalero.euroapi.domain.dto;
import com.svalero.euroapi.domain.Song;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistOutDto {
    private long id;
    private String name;
    private LocalDate birthDate;
    private String originCountry;
    private int publishedCds;
    private boolean active;
    private float igFollowers;
    private List<Song> songs;
}
