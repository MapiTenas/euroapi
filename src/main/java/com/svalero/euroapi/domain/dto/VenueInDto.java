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

public class VenueInDto {
    @NotBlank(message = "Venue name field is obligatory.")
    private String venueName;
    @NotBlank(message = "City name field is obligatory.")
    private String city;
    @Min(value = 1000, message = "Capacity venue must be greater than 1000.")
    private int capacity;
    private LocalDate foundationDate;
    private boolean adapted;
    private float latitude;
    private float longitude;
}
