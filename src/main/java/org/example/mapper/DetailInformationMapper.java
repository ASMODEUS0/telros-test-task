package org.example.mapper;

import org.example.dto.create.DetailInformationCreateDto;
import org.example.dto.read.DetailInformationReadDto;
import org.example.dto.update.DetailInformationUpdateDto;
import org.example.entity.DetailInformation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DetailInformationMapper {
    @Mapping(target = "clientId", source = "id")
    DetailInformationReadDto mapToReadDto(DetailInformation detailInformation);
    DetailInformation mapToEntity(DetailInformationCreateDto detailInformationCreateDto);
    void mapToUpdatedEntity(@MappingTarget DetailInformation detailInformation, DetailInformationUpdateDto detailInformationUpdateDto);
}
