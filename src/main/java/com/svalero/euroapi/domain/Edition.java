package com.svalero.euroapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
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
@Entity(name = "editions")
public class Edition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name ="editionNum")
    private Integer editionNum;
    @Column
    private String romanNumeralEdition;
    @Column
    private String countryOrganizer;
    @Column
    private LocalDate finalDate;
    @Column
    private String slogan;
    @Column
    private boolean cancelled;
    @Column
    private float totalBudget;

    @OneToMany (mappedBy = "edition")
    @JsonIgnore
    private List<Song> songs;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    @JsonBackReference
    private Venue venue;
}
