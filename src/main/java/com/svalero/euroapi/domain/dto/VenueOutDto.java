package com.svalero.euroapi.domain.dto;

import com.svalero.euroapi.domain.Edition;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueOutDto {
    private long id;
    private String venueName;
    private String city;
    private int capacity;
    private LocalDate foundationDate;
    private boolean adapted;
    private float latitude;
    private float longitude;
    private List<Edition> editions;
}
