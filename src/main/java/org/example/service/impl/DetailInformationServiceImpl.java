package org.example.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.create.DetailInformationCreateDto;
import org.example.dto.read.DetailInformationReadDto;
import org.example.dto.update.DetailInformationUpdateDto;
import org.example.entity.Client;
import org.example.mapper.DetailInformationMapper;
import org.example.repository.ClientRepository;
import org.example.repository.DetailInformationRepository;
import org.example.service.DetailInformation;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DetailInformationServiceImpl implements DetailInformation {

    private final DetailInformationRepository detailInformationRepository;
    private final DetailInformationMapper detailInformationMapper;
    private final ClientRepository clientRepository;

    @Transactional
    @Override
    public Optional<DetailInformationReadDto> findById(Long id) {
        return detailInformationRepository
                .findById(id)
                .map(detailInformationMapper::mapToReadDto);
    }

    @Transactional
    @Override
    public Optional<DetailInformationReadDto> create(DetailInformationCreateDto detailInformationCreateDto) {
        Optional<Client> mayBeEntity = clientRepository.findById(detailInformationCreateDto.getClientId());

        return mayBeEntity.flatMap(client -> Optional.of(detailInformationMapper.mapToEntity(detailInformationCreateDto))
                .map(detailInformation -> {
                    detailInformation.setClient(client);
                    return detailInformation;
                })
                .map(detailInformationRepository::save)
                .map(detailInformationMapper::mapToReadDto));


    }

    @Transactional
    @Override
    public Optional<DetailInformationReadDto> update(DetailInformationUpdateDto detailInformationUpdateDto) {
        return detailInformationRepository
                .findById(detailInformationUpdateDto.getClientId())
                .map(entity -> {
                    detailInformationMapper.mapToUpdatedEntity(entity, detailInformationUpdateDto);
                    return entity;
                })
                .map(detailInformationRepository::save)
                .map(detailInformationMapper::mapToReadDto);
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        return detailInformationRepository.findById(id)
                .map(entity -> {
                    detailInformationRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }
}
