package com.svalero.euroapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name= "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column(name ="birth_date")
    private LocalDate birthDate;
    @Column
    private int publishedCds;
    @Column
    private boolean active;
    @Column
    private float igFollowers;
}
