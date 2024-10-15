package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.util.RegexUtil;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class ClientBaseDtoAbs {

    @Size(min = 3, max = 64)
    public String firstName;

    @Size(min = 3, max = 64)
    public String surName;

    @Email
    @NotBlank
    public String email;

    @Pattern(regexp = RegexUtil.PHONE_PATTERN, message = "Should be like +79047648394")
    public String phoneNumber;

}
