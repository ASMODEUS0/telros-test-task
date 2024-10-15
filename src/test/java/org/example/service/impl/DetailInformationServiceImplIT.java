package org.example.service.impl;

import jakarta.validation.UnexpectedTypeException;
import org.annotation.IntegrationTest;
import org.example.dto.create.ClientCreateDto;
import org.example.dto.create.DetailInformationCreateDto;
import org.example.dto.read.ClientReadDto;
import org.example.dto.read.DetailInformationReadDto;
import org.example.dto.update.DetailInformationUpdateDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Optional;

class DetailInformationServiceImplIT extends IntegrationTest {

    @Autowired
    public DetailInformationServiceImpl detailInformationService;
    @Autowired
    public ClientServiceImpl clientService;


    private final ClientCreateDto CLIENT_CREATE_DTO = new ClientCreateDto("fName",
            "surName",
            "email@mail.com",
            "+790123423");

    private DetailInformationReadDto DETAIL_INFORMATION_DTO;


    @BeforeEach
    void setUp() {
        ClientReadDto client = clientService.createClient(CLIENT_CREATE_DTO);
        DetailInformationCreateDto detailInformationCreateDto = new DetailInformationCreateDto(client.id, Instant.now(), "");
        DETAIL_INFORMATION_DTO = detailInformationService.create(detailInformationCreateDto).orElseThrow(UnexpectedTypeException::new);
    }

    @Test
    void testFindCreatedDetailInformation() {

        Optional<DetailInformationReadDto> mayBeDetailInformation = detailInformationService.findById(DETAIL_INFORMATION_DTO.getClientId());

        Assertions.assertTrue(mayBeDetailInformation.isPresent());
        Assertions.assertEquals(DETAIL_INFORMATION_DTO.getClientId(), mayBeDetailInformation.get().clientId);
    }

    @Test
    void update() {
        DetailInformationUpdateDto updatedDetailInformation = new DetailInformationUpdateDto(DETAIL_INFORMATION_DTO.clientId, Instant.now(), "");

        Optional<DetailInformationReadDto> mayBeUpdatedDetailInformation = detailInformationService.update(updatedDetailInformation);

        Assertions.assertTrue(mayBeUpdatedDetailInformation.isPresent());
        Assertions.assertTrue(mayBeUpdatedDetailInformation.get().birthdate.getNano() > DETAIL_INFORMATION_DTO.getBirthdate().getNano());
    }

    @Test
    void delete() {
        detailInformationService.delete(DETAIL_INFORMATION_DTO.getClientId());

        Optional<DetailInformationReadDto> mayBeDetailInformation = detailInformationService.findById(DETAIL_INFORMATION_DTO.getClientId());
        Assertions.assertFalse(mayBeDetailInformation.isPresent());
    }
}