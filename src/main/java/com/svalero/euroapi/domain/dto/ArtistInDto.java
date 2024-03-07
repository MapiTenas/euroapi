package com.svalero.euroapi.domain.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistInDto {
    @NotBlank(message ="Artist name field is obligatory.")
    private String name;
    @Past(message = "The date of birth must be before the current date")
    private LocalDate birthDate;
    @NotBlank(message ="Artist's origin country field is obligatory.")
    private String originCountry;
    private int publishedCds;
    private boolean active;
    private float igFollowers;
}
