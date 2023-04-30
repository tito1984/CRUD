package com.example.crud.todo.crud.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

        Publication publication = new Publication();

        publication.mapDTO(publicationDTO);

        Publication newPublication = publicationRepository.save(publication);

        PublicationDTO publicationAnswerDto = newPublication.mapEntity();

        return publicationAnswerDto;
    }

    @Override
    public PublicationResponse getAllPublications(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Publication> publications = publicationRepository.findAll(pageable);

        List<Publication> publicationList = publications.getContent();
        List<PublicationDTO> content = publicationList.stream().map(publication -> publication.mapEntity())
                .collect(Collectors.toList());

        PublicationResponse publicationResponse = new PublicationResponse();
        publicationResponse.setContent(content);
        publicationResponse.setPageNumber(publications.getNumber());
        publicationResponse.setPageSize(publications.getSize());
        publicationResponse.setTotalElements(publications.getTotalElements());
        publicationResponse.setTotalPages(publications.getTotalPages());
        publicationResponse.setLastPage(publications.isLast());

        return publicationResponse;
    }

    @Override
    public PublicationDTO getPublicationById(long id) {
        Publication publication = publicationRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Publication", "id", id));

        return publication.mapEntity();
    }

    @Override
    public PublicationDTO updatePublication(PublicationDTO publicationDTO, long id) {
        Publication publication = publicationRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Publication", "id", id));

        publication.setTitle(publicationDTO.getTitle());
        publication.setDescription(publicationDTO.getDescription());
        publication.setContent(publicationDTO.getContent());

        Publication publicationUpdated = publicationRepository.save(publication);

        return publicationUpdated.mapEntity();
    }

    @Override
    public void deletePublication(long id) {
        Publication publication = publicationRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Publication", "id", id));

        publicationRepository.delete(publication);
    }

}
