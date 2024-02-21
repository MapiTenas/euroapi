package com.svalero.euroapi.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String title;
    @Column
    private String duration;
    @Column
    private String languaje;
    @Column
    private int votes;
    @Column
    private boolean winner;
    @Column(name ="admission_date")
    private LocalDate admissionDate;
}
