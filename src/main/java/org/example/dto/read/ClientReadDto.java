package org.example.dto.read;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.dto.ClientBaseDtoAbs;

@Getter
@NoArgsConstructor
public class ClientReadDto extends ClientBaseDtoAbs {
    public Long id;
    public String imagePath;

}
