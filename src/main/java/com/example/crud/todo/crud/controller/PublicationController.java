package com.example.crud.todo.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.todo.crud.dto.PublicationDTO;
import com.example.crud.todo.crud.dto.PublicationResponse;
import com.example.crud.todo.crud.service.PublicationService;
import com.example.crud.todo.crud.utilities.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @GetMapping
    public PublicationResponse listPublications(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.NUMBER_OF_PAGE_BY_DEFAULT, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.SIZE_OF_PAGE_BY_DEFAULT, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_DEFAULT, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIRECTION_BY_DEFAULT, required = false) String sortDir) {
        return publicationService.getAllPublications(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationDTO> getPublicationById(@PathVariable(name = "id") long id) {
        try {
            return ResponseEntity.ok(publicationService.getPublicationById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(publicationService.getPublicationById(id));
        }

    }

    @PostMapping
    public ResponseEntity<PublicationDTO> savePublication(@Valid @RequestBody PublicationDTO publicationDTO) {
        return new ResponseEntity<PublicationDTO>(publicationService.publicationCreate(publicationDTO),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicationDTO> updatePublication(@Valid @RequestBody PublicationDTO publicationDTO,
            @PathVariable(name = "id") long id) {
        try {
            PublicationDTO publicationResponse = publicationService.updatePublication(publicationDTO, id);
            return new ResponseEntity<>(publicationResponse, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(publicationService.updatePublication(publicationDTO, id));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePublication(@PathVariable(name = "id") long id) {
        try {
            publicationService.deletePublication(id);
            return new ResponseEntity<>("Publication deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }
    }
}
