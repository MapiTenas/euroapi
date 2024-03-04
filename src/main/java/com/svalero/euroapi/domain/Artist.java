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
@Entity(name= "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message ="Artist's name is obligatory.")
    @Column
    private String name;
    @Past(message = "The date of birth must be before the current date")
    @Column(name ="birth_date")
    private LocalDate birthDate;
    @NotBlank(message ="Artist's origin country is obligatory.")
    @Column
    private String originCountry;
    @Column
    private int publishedCds;
    @Column
    private boolean active;
    @Column
    private float igFollowers;

    @OneToMany(mappedBy = "artist")
    private List<Song> songs;
}
