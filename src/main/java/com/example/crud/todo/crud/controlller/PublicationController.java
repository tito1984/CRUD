package com.example.crud.todo.crud.controlller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.todo.crud.dto.PublicationDTO;
import com.example.crud.todo.crud.service.PublicationService;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @PostMapping
    public ResponseEntity<PublicationDTO> savePublication(@RequestBody PublicationDTO publicationDTO) {
        return new ResponseEntity<PublicationDTO>(publicationService.publicationCreate(publicationDTO),HttpStatus.CREATED);
    }
    
}
