package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String surName;
    private String email;
    private String phoneNumber;
    private String imagePath;

}
