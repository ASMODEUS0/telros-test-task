package org.example.service.impl;


import jakarta.validation.ConstraintViolationException;
import org.annotation.IntegrationTest;
import org.example.dto.create.ClientCreateDto;
import org.example.dto.read.ClientReadDto;
import org.example.dto.update.ClientUpdateDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceImplIT extends IntegrationTest {

    @Autowired
    private ClientServiceImpl clientService;


    private final ClientCreateDto CLIENT_CREATE_DTO = new ClientCreateDto("fName",
            "surName",
            "email@mail.com",
            "+790123423");

    private ClientReadDto CREATED_CLIENT_DTO;

    @BeforeEach
    void setUp(){
      CREATED_CLIENT_DTO = clientService.createClient(CLIENT_CREATE_DTO);
    }


    @Test
    void findById() {
        Optional<ClientReadDto> mayBeClient = clientService.findById(CREATED_CLIENT_DTO.id);

        assertTrue(mayBeClient.isPresent());
        assertEquals(CREATED_CLIENT_DTO.id, mayBeClient.get().id);
    }

    @Test
    void createClient() {
        Optional<ClientReadDto> mayBeClient = clientService.findById(CREATED_CLIENT_DTO.id);

        assertTrue(mayBeClient.isPresent());
        assertEquals(CREATED_CLIENT_DTO.id, mayBeClient.get().id);
    }

    @Test
    void updateClient() {
        String updatedFirstName = CREATED_CLIENT_DTO.firstName + "+";
        ClientUpdateDto clientUpdateDto = new ClientUpdateDto(CREATED_CLIENT_DTO.id,
                updatedFirstName,
                CREATED_CLIENT_DTO.surName,
                CREATED_CLIENT_DTO.email,
                CREATED_CLIENT_DTO.phoneNumber);

        Optional<ClientReadDto> mayBeUpdatedClient = clientService.updateClient(clientUpdateDto);


        assertTrue(mayBeUpdatedClient.isPresent());
        assertEquals(mayBeUpdatedClient.get().id, CREATED_CLIENT_DTO.id);
        assertEquals(mayBeUpdatedClient.get().firstName, updatedFirstName);
        assertNotEquals(CREATED_CLIENT_DTO.firstName, updatedFirstName);
    }

    @Test
    void removeById() {
        boolean removed = clientService.removeById(CREATED_CLIENT_DTO.id);
        Optional<ClientReadDto> mayBeClient = clientService.findById(CREATED_CLIENT_DTO.id);

        assertTrue(removed);
        assertFalse(mayBeClient.isPresent());
    }

    @Test
    void testThatTheImageBeingSaved() {
        byte[] imageStub = "Hello world".getBytes();
        MockMultipartFile multipartFileMock = new MockMultipartFile("image", "image.png", "multipart/form-data", imageStub);

        Optional<ClientReadDto> clientReadDto = clientService.setImage(CREATED_CLIENT_DTO.id, multipartFileMock);
        Optional<byte[]> image = clientService.findImage(CREATED_CLIENT_DTO.id);

        assertTrue(image.isPresent());
        assertArrayEquals(image.get(), imageStub);
        assertTrue(clientReadDto.isPresent());
        assertEquals(clientReadDto.get().imagePath, multipartFileMock.getOriginalFilename());
    }

    @Nested
    class ValidationTest {

        private ClientCreateDto VALID_CLIENT_CREATE_DTO;

        @BeforeEach
        void setUp() {
            VALID_CLIENT_CREATE_DTO = new ClientCreateDto("fName",
                    "surName",
                    "email@mail.com",
                    "+790123423");
        }

        @Test
        @DisplayName("test that firstName size is not < 3")
        void testFirstNameLessSize() {
            VALID_CLIENT_CREATE_DTO.firstName = String.format("%-2s", "");
            assertThrows(ConstraintViolationException.class,
                    () -> clientService.createClient(VALID_CLIENT_CREATE_DTO));
        }


        @Test
        @DisplayName("test that firstName size is not > 64")
        void testFirstNameMoreSize() {
            VALID_CLIENT_CREATE_DTO.firstName = String.format("%-65s", "");
            assertThrows(ConstraintViolationException.class,
                    () -> clientService.createClient(VALID_CLIENT_CREATE_DTO));
        }


        @Test
        @DisplayName("test that surname size is not < 3")
        void testSurnameLessSize() {
            VALID_CLIENT_CREATE_DTO.surName = String.format("%-2s", "");
            assertThrows(ConstraintViolationException.class,
                    () -> clientService.createClient(VALID_CLIENT_CREATE_DTO));
        }


        @Test
        @DisplayName("test that surname size is not > 64")
        void testSurnameMoreSize() {
            VALID_CLIENT_CREATE_DTO.surName = String.format("%-65s", "");
            assertThrows(ConstraintViolationException.class,
                    () -> clientService.createClient(VALID_CLIENT_CREATE_DTO));
        }


        @Test
        @DisplayName("test that email is not blank ")
        void testEmailNotBlank() {
            String blankEmail = "";
            VALID_CLIENT_CREATE_DTO.email = blankEmail;
            assertThrows(ConstraintViolationException.class,
                    () -> clientService.createClient(VALID_CLIENT_CREATE_DTO));
        }

        @Test
        @DisplayName("test that email is valid")
        void testEmailIsValid() {
            String invalidEmail = "aaaaa.com";
            VALID_CLIENT_CREATE_DTO.email = invalidEmail;
            assertThrows(ConstraintViolationException.class,
                    () -> clientService.createClient(VALID_CLIENT_CREATE_DTO));
        }


        @Test
        @DisplayName("test that phone number is valid")
        void testPhoneNumberIsValid() {
            String invalidPhoneNumber = "+7893";
            VALID_CLIENT_CREATE_DTO.email = invalidPhoneNumber;
            assertThrows(ConstraintViolationException.class,
                    () -> clientService.createClient(VALID_CLIENT_CREATE_DTO));
        }


    }

}