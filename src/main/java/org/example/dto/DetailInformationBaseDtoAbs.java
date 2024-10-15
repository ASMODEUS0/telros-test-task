package org.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class DetailInformationBaseDtoAbs {
    @NotNull
    public Long clientId;
    public Instant birthdate;
    public String lastName;
}
