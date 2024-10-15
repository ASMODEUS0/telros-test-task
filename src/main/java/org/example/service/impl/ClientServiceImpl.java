package org.example.service.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.create.ClientCreateDto;
import org.example.dto.read.ClientReadDto;
import org.example.dto.update.ClientUpdateDto;
import org.example.entity.Client;
import org.example.mapper.ClientMapper;
import org.example.repository.ClientRepository;
import org.example.service.ClientService;
import org.example.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ImageService imageService;

    @Transactional
    @Override
    public Optional<ClientReadDto> findById(Long id) {
        Optional<Client> byId = clientRepository.findById(id);
        return byId.map(clientMapper::mapToDto);
    }

    @Transactional
    @Override
    public ClientReadDto createClient(@Valid ClientCreateDto clientCreateDto) {
        Client client = clientMapper.mapToEntity(clientCreateDto);
        Client savedClient = clientRepository.save(client);
        return clientMapper.mapToDto(savedClient);
    }

    @Transactional
    @Override
    public Optional<ClientReadDto> updateClient(@Valid ClientUpdateDto clientUpdateDto) {
        return clientRepository
                .findById(clientUpdateDto.getId())
                .map(client -> {
                    clientMapper.mapToUpdatedEntity(client, clientUpdateDto);
                    return client;
                })
                .map(clientRepository::save)
                .map(clientMapper::mapToDto);

    }

    @Transactional
    @Override
    public boolean removeById(Long id) {
        return clientRepository.findById(id)
                .map(client -> {
                    clientRepository.delete(client);
                    return true;
                })
                .orElse(false);

    }

    @Transactional
    @Override
    public Optional<ClientReadDto> setImage(Long id, MultipartFile image) {
        return clientRepository.findById(id)
                .map(entity -> {
                    entity.setImagePath(image.getOriginalFilename());
                    imageService.uploadImage(image);
                    return entity;
                })
                .map(clientRepository::save)
                .map(clientMapper::mapToDto);
    }

    @Transactional
    @Override
    public Optional<byte[]> findImage(Long id) {
        return clientRepository.findById(id)
                .map(Client::getImagePath)
                .filter(StringUtils::hasText)
                .flatMap(imageService::getImage);
    }

}
