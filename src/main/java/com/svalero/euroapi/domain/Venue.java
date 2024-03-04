package com.svalero.euroapi.domain;
import jakarta.persistence.*;
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
@Entity(name ="venues")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Venue name field is obligatory.")
    @Column
    private String venueName;
    @NotBlank(message = "City name field is obligatory.")
    @Column
    private String city;
    @Column
    @Min(value = 1000, message = "Capacity venue must be greater than 1000.")
    private int capacity;
    @Column
    private LocalDate foundationDate;
    @Column
    private boolean adapted;
    @Column
    private float latitude;
    @Column
    private float longitude;

    @OneToMany(mappedBy = "venue")
    private List<Edition> editions;
}
