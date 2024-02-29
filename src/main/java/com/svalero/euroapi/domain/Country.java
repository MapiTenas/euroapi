package com.svalero.euroapi.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String countryName;
    @Column
    private boolean bigFive;
    @Column
    private int editionsWinned;
    @Column
    private String publicBroadcaster;
    @Column
    private float participationFee;
    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @OneToMany(mappedBy = "country")
    private List<Song> songs;

}
