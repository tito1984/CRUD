package com.example.crud.todo.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crud.todo.crud.dto.PublicationDTO;
import com.example.crud.todo.crud.entities.Publication;
import com.example.crud.todo.crud.repository.PublicationRepository;

@Service
public class PublicationServiceImpl implements PublicationService{

    @Autowired
    private PublicationRepository publicationRepository;

    @Override
    public PublicationDTO publicationCreate(PublicationDTO publicationDTO) {
        // Converto from DTO to entity
        Publication publication = new Publication();
        publication.setTitle(publicationDTO.getTitle());
        publication.setDescription(publicationDTO.getDescription());
        publication.setContent(publicationDTO.getContent());

        Publication newPublication = publicationRepository.save(publication);

        // Convert from entity to DTO
        PublicationDTO publicationResponse = new PublicationDTO();
        publicationResponse.setId(newPublication.getId());
        publicationResponse.setTitle(newPublication.getTitle());
        publicationResponse.setDescription(newPublication.getDescription());
        publicationResponse.setContent(newPublication.getContent());

        return publicationResponse;
    }
    
}
