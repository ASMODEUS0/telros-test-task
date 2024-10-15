package org.example.service;

import jakarta.validation.Valid;
import org.example.dto.create.DetailInformationCreateDto;
import org.example.dto.read.DetailInformationReadDto;
import org.example.dto.update.DetailInformationUpdateDto;

import java.util.Optional;

public interface DetailInformation {

    Optional<DetailInformationReadDto> findById(Long id);

    Optional<DetailInformationReadDto> create(@Valid DetailInformationCreateDto detailInformationCreateDto);

    Optional<DetailInformationReadDto> update(@Valid DetailInformationUpdateDto detailInformationUpdateDto);

    boolean delete(Long id);
}
