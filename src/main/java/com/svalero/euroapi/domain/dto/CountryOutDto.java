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
public class CountryOutDto {
    private long id;
    private String countryName;
    private boolean bigFive;
    private int editionsWinned;
    private String publicBroadcaster;
    private List<Song> songs;

}
