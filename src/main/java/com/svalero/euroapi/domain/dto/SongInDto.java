package com.svalero.euroapi.domain.dto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SongInDto {
    @NotBlank(message = "Song title field is obligatory.")
    @Column
    private String title;
    @Column
    private float duration;
    @NotBlank(message = "Song language field is obligatory.")
    @Column
    private String language;
    @Min(value = 0, message = "Votes cannot be less than zero.")
    @Column
    private int votes;
    @Column
    private boolean winner;
    @Column(name ="admission_date")
    private LocalDate admissionDate;
    private Long artistId;
    private Long countryId;
    private Long editionId;

}
