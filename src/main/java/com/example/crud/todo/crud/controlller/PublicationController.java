package com.example.crud.todo.crud.controlller;


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

@RestController
@RequestMapping("/api/publications")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @GetMapping
    public PublicationResponse listPublications(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.NUMBER_OF_PAGE_BY_DEFECT, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.SIZE_OF_PAGE_BY_DEFECT, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_DEFECT, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIRECTION_BY_DEFECT, required = false) String sortDir) {
        return publicationService.getAllPublications(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationDTO> optenerPublicationById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(publicationService.getPublicationById(id));
    }

    @PostMapping
    public ResponseEntity<PublicationDTO> savePublication(@RequestBody PublicationDTO publicationDTO) {
        return new ResponseEntity<PublicationDTO>(publicationService.publicationCreate(publicationDTO),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicationDTO> updatePublication(@RequestBody PublicationDTO publicationDTO,
            @PathVariable(name = "id") long id) {
        PublicationDTO publicationResponse = publicationService.updatePublication(publicationDTO, id);
        return new ResponseEntity<>(publicationResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePublication(@PathVariable(name = "id") long id) {
        publicationService.deletePublication(id);
        return new ResponseEntity<>("Publication deleted successfully", HttpStatus.OK);
    }
}
