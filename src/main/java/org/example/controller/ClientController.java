package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.dto.create.ClientCreateDto;
import org.example.dto.read.ClientReadDto;
import org.example.dto.update.ClientUpdateDto;
import org.example.service.ClientService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(path = "/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping(path = "/create")
    public ResponseEntity<ClientReadDto> create(@RequestBody ClientCreateDto clientCreateDto) {
        ClientReadDto client = clientService.createClient(clientCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }

    @GetMapping(path = "/find")
    public ResponseEntity<ClientReadDto> findById(@RequestParam Long id) {
        return clientService
                .findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/update")
    public ResponseEntity<ClientReadDto> updateClient(@RequestBody ClientUpdateDto clientUpdateDto) {
        return clientService.updateClient(clientUpdateDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Void> removeById(@RequestParam Long id) {
        return clientService.removeById(id) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/images/upload")
    public ResponseEntity<ClientReadDto> uploadImage(@RequestParam(name = "id") Long id,
                                                     @RequestParam(name = "image") MultipartFile image) {
        return clientService.setImage(id, image)
                .map(clientReadDto -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(clientReadDto))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/images", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getImage(@RequestParam Long id) {
        return clientService.findImage(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElse(ResponseEntity.notFound().build());
    }

}
