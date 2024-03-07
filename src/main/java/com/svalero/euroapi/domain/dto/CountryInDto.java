package com.svalero.euroapi.domain.dto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryInDto {
    @NotBlank(message ="Country name field is obligatory.")
    private String countryName;
    private boolean bigFive;
    private int editionsWinned;
    @NotBlank(message ="Public broadcaster field is obligatory.")
    private String publicBroadcaster;
    private float participationFee;
    @Past(message = "Joining date must be before the current date.")
    private LocalDate joiningDate;

}
