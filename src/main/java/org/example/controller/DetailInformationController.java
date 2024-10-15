package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.create.DetailInformationCreateDto;
import org.example.dto.read.DetailInformationReadDto;
import org.example.dto.update.DetailInformationUpdateDto;
import org.example.service.DetailInformation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/clients/detail")
@RequiredArgsConstructor
public class DetailInformationController  {
    private final DetailInformation detailInformationService;


    @GetMapping(path = "/find")
    public ResponseEntity<DetailInformationReadDto> findById(Long id) {
        return detailInformationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/create")
    public ResponseEntity<DetailInformationReadDto> create(@RequestBody DetailInformationCreateDto detailInformationCreateDto) {
        return detailInformationService
                .create(detailInformationCreateDto)
                .map(dto -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/update")
    public ResponseEntity<DetailInformationReadDto> update(@RequestBody DetailInformationUpdateDto detailInformationUpdateDto) {
        return detailInformationService
                .update(detailInformationUpdateDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping(path = "/delete")
    public ResponseEntity<Object> deleteById(Long id) {
        return detailInformationService.delete(id) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

}
