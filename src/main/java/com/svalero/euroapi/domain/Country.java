package com.svalero.euroapi.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
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
    @NotBlank(message ="Country name field is obligatory.")
    @Column
    private String countryName;
    @Column
    private boolean bigFive;
    @Column
    private int editionsWinned;
    @NotBlank(message ="Public broadcaster field is obligatory.")
    @Column
    private String publicBroadcaster;
    @Column
    private float participationFee;
    @Past(message = "Joining date must be before the current date.")
    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @OneToMany(mappedBy = "country")
    private List<Song> songs;

}
