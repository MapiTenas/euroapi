package com.svalero.euroapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

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
    @Column
    private int edition;
    @Column
    private String romanNumeralEdition;
    @Column
    private LocalDate finalDate;
    @Column
    private String slogan;
    @Column
    private boolean cancelled;
    @Column
    private float totalBudget;

    @OneToMany (mappedBy = "edition")
    private List<Song> songs;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;








}
