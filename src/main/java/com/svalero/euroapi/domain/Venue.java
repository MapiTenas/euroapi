package com.svalero.euroapi.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name ="venues")
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String venueName;
    @Column
    private int capacity;
    @Column
    private LocalDate foundationDate;
    @Column
    private boolean adapted;
    @Column
    private float latitude;
    @Column
    private float longitude;
}
