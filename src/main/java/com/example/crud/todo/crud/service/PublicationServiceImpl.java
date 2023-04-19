package com.example.crud.todo.crud.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.crud.todo.crud.dto.PublicationDTO;
import com.example.crud.todo.crud.dto.PublicationResponse;
import com.example.crud.todo.crud.entities.Publication;
import com.example.crud.todo.crud.exceptions.ResourceNotFoundException;
import com.example.crud.todo.crud.repository.PublicationRepository;

@Service
public class PublicationServiceImpl implements PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    @Override
    public PublicationDTO publicationCreate(PublicationDTO publicationDTO) {

        Publication publication = mapEntity(publicationDTO);

        Publication newPublication = publicationRepository.save(publication);

        PublicationDTO publicationAnswerDto = mapDTO(newPublication);

        return publicationAnswerDto;
    }

    @Override
    public PublicationResponse getAllPublications(int pageNo, int pageSize) {
        PageRequest pageable = PageRequest.of(pageNo,pageSize);

        Page<Publication> publications = publicationRepository.findAll(pageable);

        List<Publication> publicationList = publications.getContent();
        List<PublicationDTO> content = publicationList.stream().map(publication -> mapDTO(publication)).collect(Collectors.toList());

        PublicationResponse publicationResponse = new PublicationResponse();
        publicationResponse.setContent(content);
        publicationResponse.setPageNumber(publications.getNumber());
        publicationResponse.setPageSize(publications.getSize());
        publicationResponse.setTotalElements(publications.getTotalElements());
        publicationResponse.setTotalPages(publications.getTotalPages());
        publicationResponse.setLastPage(publications.isLast());

        return publicationResponse;
    }

    // Convert from entity to DTO
    private PublicationDTO mapDTO(Publication publication) {
        PublicationDTO publicationDTO = new PublicationDTO();

        publicationDTO.setId(publication.getId());
        publicationDTO.setTitle(publication.getTitle());
        publicationDTO.setDescription(publication.getDescription());
        publicationDTO.setContent(publication.getContent());

        return publicationDTO;
    }

    // Converto from DTO to entity
    private Publication mapEntity(PublicationDTO publicationDTO) {
        Publication publication = new Publication();

        publication.setTitle(publicationDTO.getTitle());
        publication.setDescription(publicationDTO.getDescription());
        publication.setContent(publicationDTO.getContent());

        return publication;
    }

    @Override
    public PublicationDTO getPublicationById(long id) {
        Publication publication = publicationRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Publication", "id", id));

        return mapDTO(publication);
    }

    @Override
    public PublicationDTO updatePublication(PublicationDTO publicationDTO, long id) {
        Publication publication = publicationRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Publication", "id", id));

        publication.setTitle(publicationDTO.getTitle());
        publication.setDescription(publicationDTO.getDescription());
        publication.setContent(publicationDTO.getContent());

        Publication publicationUpdated = publicationRepository.save(publication);

        return mapDTO(publicationUpdated);
    }

    @Override
    public void deletePublication(long id) {
        Publication publication = publicationRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Publication", "id", id));

        publicationRepository.delete(publication);
    }
}
