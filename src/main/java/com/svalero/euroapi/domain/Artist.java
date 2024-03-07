package com.svalero.euroapi.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column
    private String name;
    @Column(name ="birth_date")
    private LocalDate birthDate;
    @Column
    private String originCountry;
    @Column
    private int publishedCds;
    @Column
    private boolean active;
    @Column
    private float igFollowers;

    @OneToMany(mappedBy = "artist")
    @JsonIgnore
    private List<Song> songs;
}
