package org.example.dto.update;

import org.example.dto.DetailInformationBaseDtoAbs;
import java.time.Instant;

public class DetailInformationUpdateDto extends DetailInformationBaseDtoAbs {

    public DetailInformationUpdateDto(Long clientId,
                                      Instant birthdate,
                                      String lastName) {
        super(clientId, birthdate, lastName);
    }
}
