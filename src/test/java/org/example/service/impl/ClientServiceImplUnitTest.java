package org.example.service.impl;

import org.example.dto.create.ClientCreateDto;
import org.example.dto.read.ClientReadDto;
import org.example.dto.update.ClientUpdateDto;
import org.example.entity.Client;
import org.example.mapper.ClientMapper;
import org.example.mapper.ClientMapperImpl;
import org.example.repository.ClientRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplUnitTest {

    private final String EMAIL = "email";
    private final String PHONE_NUMBER = "phoneNumber";
    private final String SURNAME = "surname";
    private final String FIRST_NAME = "firstName";

    private Client CLIENT_ENTITY;

    @Mock
    private ClientRepository clientRepository;
    @Spy
    private ClientMapper clientMapper = new ClientMapperImpl();
    @Mock
    private ImageServiceImpl imageService;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {

        String IMAGE_PATH = "imagePath";
        CLIENT_ENTITY = Client.builder()
                .id(1L)
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .surName(SURNAME)
                .firstName(FIRST_NAME)
                .imagePath(IMAGE_PATH)
                .build();

    }

    @Test
    void testIfRepositoryDontFindClientServiceWillReturnEmpty() {
        Mockito.when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ClientReadDto> mayBeClient = clientService.findById(1L);

        assertTrue(mayBeClient.isEmpty());
    }


    @Test
    void testIfRepositoryFindClientServiceWillReturnClient() {
        Mockito.when(clientRepository.findById(1L)).thenReturn(Optional.of(CLIENT_ENTITY));

        Optional<ClientReadDto> mayBeClient = clientService.findById(1L);

        assertTrue(mayBeClient.isPresent());
        assertEquals(EMAIL, mayBeClient.get().email);
        assertEquals(PHONE_NUMBER, mayBeClient.get().phoneNumber);
        assertEquals(SURNAME, mayBeClient.get().surName);
        assertEquals(FIRST_NAME, mayBeClient.get().firstName);

    }


    @Test
    void testThatIfRepositorySaveReturnClient() {
        ClientCreateDto CLIENT_CREATE_DTO = new ClientCreateDto(FIRST_NAME, SURNAME, EMAIL, PHONE_NUMBER);
        Mockito.when(clientRepository.save(CLIENT_ENTITY)).thenReturn(CLIENT_ENTITY);
        Mockito.when(clientMapper.mapToEntity(CLIENT_CREATE_DTO)).thenReturn(CLIENT_ENTITY);

        ClientReadDto client = clientService.createClient(CLIENT_CREATE_DTO);

        assertEquals(EMAIL, client.email);
        assertEquals(PHONE_NUMBER, client.phoneNumber);
        assertEquals(SURNAME, client.surName);
        assertEquals(FIRST_NAME, client.firstName);
    }

    @Test
    void testThatIfRepositoryDoNotSaveReturnEmpty() {
        ClientCreateDto CLIENT_CREATE_DTO = new ClientCreateDto(FIRST_NAME, SURNAME, EMAIL, PHONE_NUMBER);
        Mockito.when(clientRepository.save(CLIENT_ENTITY)).thenThrow(new IllegalArgumentException());
        Mockito.when(clientMapper.mapToEntity(CLIENT_CREATE_DTO)).thenReturn(CLIENT_ENTITY);

        assertThrows(IllegalArgumentException.class, () -> clientService.createClient(CLIENT_CREATE_DTO));
    }

    @Test
    void testThatIfRepositoryFindClientAndUpdateUpdateClientReturnClient() {
        ClientUpdateDto clientUpdateDto = new ClientUpdateDto(CLIENT_ENTITY.getId(), FIRST_NAME, SURNAME, EMAIL, PHONE_NUMBER);
        Mockito.when(clientRepository.findById(clientUpdateDto.getId())).thenReturn(Optional.of(CLIENT_ENTITY));
        Mockito.when(clientRepository.save(CLIENT_ENTITY)).thenReturn(CLIENT_ENTITY);

        Optional<ClientReadDto> mayBeClient = clientService.updateClient(clientUpdateDto);

        assertTrue(mayBeClient.isPresent());
        assertEquals(EMAIL, mayBeClient.get().email);
        assertEquals(PHONE_NUMBER, mayBeClient.get().phoneNumber);
        assertEquals(SURNAME, mayBeClient.get().surName);
        assertEquals(FIRST_NAME, mayBeClient.get().firstName);
    }


    @Test
    void testThatIfRepositoryDoNotFindClientReturnEmpty() {
        ClientUpdateDto clientUpdateDto = new ClientUpdateDto(CLIENT_ENTITY.getId(), FIRST_NAME, SURNAME, EMAIL, PHONE_NUMBER);
        Mockito.when(clientRepository.findById(clientUpdateDto.getId())).thenReturn(Optional.empty());

        Optional<ClientReadDto> mayBeClient = clientService.updateClient(clientUpdateDto);

        assertFalse(mayBeClient.isPresent());
    }

    @Test
    void testThatIfRepositoryFindEntityWithIdAndRemoveMethodDontThrowExceptionReturnTrue() {
        Mockito.when(clientRepository.findById(CLIENT_ENTITY.getId())).thenReturn(Optional.of(CLIENT_ENTITY));

        boolean deleted = clientService.removeById(CLIENT_ENTITY.getId());

        assertTrue(deleted);
    }

    @Test
    void testThatIfRepositoryFindEntityReturnFalse() {
        Mockito.when(clientRepository.findById(CLIENT_ENTITY.getId())).thenReturn(Optional.empty());

        boolean deleted = clientService.removeById(CLIENT_ENTITY.getId());

        assertFalse(deleted);
    }


    @Test
    void testThatIfRepositoryFindEntityByIdAndPersistItReturnEntityWithImage() {
        String IMAGE_NAME = "image";
        MultipartFile imageMock = Mockito.mock(MultipartFile.class);
        Mockito.when(clientRepository.findById(CLIENT_ENTITY.getId())).thenReturn(Optional.of(CLIENT_ENTITY));
        Mockito.when(imageMock.getOriginalFilename()).thenReturn(IMAGE_NAME);
        Mockito.when(clientRepository.save(CLIENT_ENTITY)).thenReturn(CLIENT_ENTITY);

        Optional<ClientReadDto> mayBeClient = clientService.setImage(CLIENT_ENTITY.getId(), imageMock);

        assertTrue(mayBeClient.isPresent());
        assertEquals(IMAGE_NAME, mayBeClient.get().imagePath);
    }


    @Test
    void testThatIfRepositoryDoNotFindEntityByIdReturnEmpty() {
        MultipartFile imageMock = Mockito.mock(MultipartFile.class);
        Mockito.when(clientRepository.findById(CLIENT_ENTITY.getId())).thenReturn(Optional.empty());

        Optional<ClientReadDto> mayBeClient = clientService.setImage(CLIENT_ENTITY.getId(), imageMock);

        assertFalse(mayBeClient.isPresent());
    }

    @Test
    void testThatIfRepositoryFindEntityAndImageServiceReturnImageReturnImageData() {
        byte[] IMAGE_DATA = "Hello World".getBytes();
        Mockito.when(clientRepository.findById(CLIENT_ENTITY.getId())).thenReturn(Optional.of(CLIENT_ENTITY));
        Mockito.when(imageService.getImage(CLIENT_ENTITY.getImagePath())).thenReturn(Optional.of(IMAGE_DATA));

        Optional<byte[]> image = clientService.findImage(CLIENT_ENTITY.getId());

        assertTrue(image.isPresent());
        assertArrayEquals(IMAGE_DATA, image.get());
    }


    @Test
    void testThatIfRepositoryDoNotFindEntityReturnEmpty() {
        Mockito.when(clientRepository.findById(CLIENT_ENTITY.getId())).thenReturn(Optional.empty());

        Optional<byte[]> image = clientService.findImage(CLIENT_ENTITY.getId());

        assertFalse(image.isPresent());
    }


}