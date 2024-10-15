package org.example.dto.read;

import org.example.dto.DetailInformationBaseDtoAbs;

import java.time.Instant;

public class DetailInformationReadDto extends DetailInformationBaseDtoAbs {
    public DetailInformationReadDto(Long clientId,
                                    Instant birthdate,
                                    String lastName) {
        super(clientId, birthdate, lastName);
    }
}
