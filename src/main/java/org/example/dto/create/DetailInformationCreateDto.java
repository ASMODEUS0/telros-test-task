package org.example.dto.create;


import lombok.Getter;
import org.example.dto.DetailInformationBaseDtoAbs;

import java.time.Instant;

@Getter
public class DetailInformationCreateDto extends DetailInformationBaseDtoAbs {

    public DetailInformationCreateDto(Long clientId,
                                      Instant birthdate,
                                      String lastName){
        super(clientId, birthdate, lastName);
    }
}
