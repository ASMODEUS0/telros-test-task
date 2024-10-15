package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DetailInformation {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "client_id")
    private Client client;
    private Instant birthdate;
    private String lastName;
}
