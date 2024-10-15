package org.example.service;

import jakarta.validation.Valid;
import org.example.dto.create.ClientCreateDto;
import org.example.dto.read.ClientReadDto;
import org.example.dto.update.ClientUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ClientService {

    Optional<ClientReadDto> findById(Long id);

    ClientReadDto createClient(@Valid ClientCreateDto clientCreateDto);

    Optional<ClientReadDto> updateClient(@Valid ClientUpdateDto clientUpdateDto);

    boolean removeById(Long id);

    Optional<ClientReadDto> setImage(Long id, MultipartFile image);

    Optional<byte[]> findImage(Long id);
}
