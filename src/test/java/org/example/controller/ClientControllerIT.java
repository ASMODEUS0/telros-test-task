package org.example.controller;

import jakarta.persistence.EntityManager;
import org.annotation.IntegrationTest;
import org.example.dto.create.ClientCreateDto;
import org.example.dto.read.ClientReadDto;
import org.example.dto.update.ClientUpdateDto;
import org.example.entity.Client;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientControllerIT extends IntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    // auth data
    @Value("${app.auth.username}")
    private String username;
    @Value("${app.auth.password}")
    private String password;

    //path
    @LocalServerPort
    private int PORT;
    private final String DOMAIN = "http://localhost:";
    private final String PATH = "/clients";


    //default data
    private final String EMAIL = "email@mail.com";
    private final String PHONE_NUMBER = "+790123423";
    private final String SURNAME = "surname";
    private final String FIRST_NAME = "firstName";

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private EntityManager entityManager;

    private String buildBaseUrlStr() {
        return DOMAIN + PORT + PATH;
    }

    @Test
    void create() {
        ClientCreateDto clientCreateDto = new ClientCreateDto(FIRST_NAME,
                SURNAME,
                EMAIL,
                PHONE_NUMBER);

        ResponseEntity<ClientReadDto> response = testRestTemplate
                .withBasicAuth(username, password)
                .exchange(
                        buildBaseUrlStr() + "/create",
                        HttpMethod.POST,
                        new HttpEntity<>(clientCreateDto),
                        ClientReadDto.class
                );
        ClientReadDto responseResult = response.getBody();
        Assertions.assertNotNull(responseResult);
        Client client = entityManager.find(Client.class, responseResult.id);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(FIRST_NAME, client.getFirstName());
        Assertions.assertEquals(SURNAME, client.getSurName());
        Assertions.assertEquals(EMAIL, client.getEmail());
        Assertions.assertEquals(PHONE_NUMBER, client.getPhoneNumber());
    }

    @Test
    void findById() {
        Client client = Client.builder()
                .email(EMAIL)
                .surName(SURNAME)
                .phoneNumber(PHONE_NUMBER)
                .firstName(FIRST_NAME)
                .build();
        transactionTemplate.execute(status -> {
            entityManager.persist(client);
            return null;
        });
        URI url = UriComponentsBuilder.fromUriString(buildBaseUrlStr() + "/find")
                .queryParam("id", client.getId())
                .build().toUri();

        ResponseEntity<ClientReadDto> response = testRestTemplate.getForEntity(url, ClientReadDto.class);
        ClientReadDto responseResult = response.getBody();

        Assertions.assertNotNull(responseResult);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(FIRST_NAME, responseResult.getFirstName());
        Assertions.assertEquals(SURNAME, responseResult.getSurName());
        Assertions.assertEquals(EMAIL, responseResult.getEmail());
        Assertions.assertEquals(PHONE_NUMBER, responseResult.getPhoneNumber());
    }

    @Test
    void updateClient() {
        Client client = Client.builder()
                .email(EMAIL)
                .surName(SURNAME)
                .phoneNumber(PHONE_NUMBER)
                .firstName(FIRST_NAME)
                .build();

        transactionTemplate.execute(status -> {
            entityManager.persist(client);
            return null;
        });
        ClientUpdateDto clientUpdateDto = new ClientUpdateDto(client.getId(),
                FIRST_NAME,
                SURNAME,
                EMAIL,
                PHONE_NUMBER);

        ResponseEntity<ClientReadDto> response = testRestTemplate
                .withBasicAuth(username, password)
                .exchange(
                        buildBaseUrlStr() + "/update",
                        HttpMethod.PUT,
                        new HttpEntity<>(clientUpdateDto),
                        ClientReadDto.class
                );

        ClientReadDto responseResult = response.getBody();

        Assertions.assertNotNull(responseResult);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(FIRST_NAME, responseResult.getFirstName());
        Assertions.assertEquals(SURNAME, responseResult.getSurName());
        Assertions.assertEquals(EMAIL, responseResult.getEmail());
        Assertions.assertEquals(PHONE_NUMBER, responseResult.getPhoneNumber());
    }

}