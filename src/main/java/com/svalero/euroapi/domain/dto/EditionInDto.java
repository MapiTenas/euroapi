package com.svalero.euroapi.domain.dto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditionInDto {
    @Min(value= 1, message = "Edition number field must be bigger than zero.")
    private Integer editionNum;
    @NotBlank(message ="Roman Numeral Edition field is obligatory.")
    private String romanNumeralEdition;
    @NotBlank(message="Country organizer field is obligatory.")
    private String countryOrganizer;
    private LocalDate finalDate;
    private String slogan;
    private boolean cancelled;
    @DecimalMin(value = "0.01", message = "Total bugdet must be greater than zero.")
    private float totalBudget;
    private Long venueId;
}
