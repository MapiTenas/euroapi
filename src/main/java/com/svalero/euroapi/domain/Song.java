package com.svalero.euroapi.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Song title field is obligatory.")
    @Column
    private String title;
    @Column
    private float duration;
    @NotBlank(message = "Song language field is obligatory.")
    @Column
    private String language;
    @Min(value = 0, message = "Votes cannot be less than zero.")
    @Column
    private int votes;
    @Column
    private boolean winner;
    @Column(name ="admission_date")
    private LocalDate admissionDate;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "edition_id")
    private Edition edition;
}
