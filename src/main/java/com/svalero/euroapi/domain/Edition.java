package com.svalero.euroapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "editions")
public class Edition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Min(value= 1, message = "Edition number field must be bigger than zero.")
    @Column
    private int edition;
    @NotBlank(message ="Roman Numeral Edition field is obligatory.")
    @Column
    private String romanNumeralEdition;
    @NotBlank(message="Country organizer field is obligatory.")
    @Column
    private String countryOrganizer;
    @Column
    private LocalDate finalDate;
    @Column
    private String slogan;
    @Column
    private boolean cancelled;
    @DecimalMin(value = "0.01", message = "Total bugdet must be greater than zero.")
    @Column
    private float totalBudget;

    @OneToMany (mappedBy = "edition")
    private List<Song> songs;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;
}
