package org.example.mapper;

import org.example.dto.create.ClientCreateDto;
import org.example.dto.read.ClientReadDto;
import org.example.dto.update.ClientUpdateDto;
import org.example.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = DetailInformationMapper.class)
public interface ClientMapper {

    ClientReadDto mapToDto(Client client);

    Client mapToEntity(ClientCreateDto clientCreateDto);

    void mapToUpdatedEntity(@MappingTarget Client client , ClientUpdateDto clientUpdateDto);
}
