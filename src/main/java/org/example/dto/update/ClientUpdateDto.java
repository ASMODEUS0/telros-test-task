package org.example.dto.update;


import lombok.Getter;
import org.example.dto.ClientBaseDtoAbs;

@Getter
public class ClientUpdateDto extends ClientBaseDtoAbs {
    public Long id;


    public ClientUpdateDto(Long id,
                         String firstName,
                         String surName,
                         String email,
                         String phoneNumber) {
        super(firstName, surName, email, phoneNumber);
        this.id = id;
    }

}
