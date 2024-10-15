package org.example.dto.create;


import lombok.Getter;
import org.example.dto.ClientBaseDtoAbs;

@Getter
public class ClientCreateDto extends ClientBaseDtoAbs {

    public ClientCreateDto(String firstName,
                           String surName,
                           String email,
                           String phoneNumber) {
        super(firstName, surName, email, phoneNumber);
    }
}
