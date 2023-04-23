package com.example.crud.todo.crud.service;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.crud.todo.crud.dto.PublicationDTO;
import com.example.crud.todo.crud.entities.Publication;
import com.example.crud.todo.crud.repository.PublicationRepository;

@ExtendWith(MockitoExtension.class)
public class PublicationServiceImplTest {

    @Mock
    private PublicationRepository publicationRepository;

    @InjectMocks
    private PublicationServiceImpl publicationService;

    private PublicationDTO publicationDTO;

    @BeforeEach
    void init() {
        publicationDTO = PublicationDTO.builder()
                .title("Nueva publicacion")
                .description("Publicacion nueva")
                .content("Esta es la publicacion de la que hablamos")
                .build();
    }


    @Test
    void testDeletePublication() {

    }

    @Test
    void testGetAllPublications() {

    }

    @Test
    void testGetPublicationById() {

    }

    @Test
    void testPublicationCreate() {
        given(publicationRepository.findById(publicationDTO.getId()))
            .willReturn(Optional.empty());
        Publication publication = new Publication();
        publication.mapDTO(publicationDTO);
        given(publicationRepository.save(publication)).willReturn(publication);

        
        PublicationDTO savedPublication = publicationService.publicationCreate(publication.mapEntity());

        assertThat(savedPublication).isNotNull();
    }

    @Test
    void testUpdatePublication() {

    }

    @Test
    void testDeletePublication2() {
        
    }

    @Test
    void testGetAllPublications2() {
        
    }

    @Test
    void testGetPublicationById2() {
        
    }

    @Test
    void testPublicationCreate2() {
        
    }

    @Test
    void testUpdatePublication2() {
        
    }
}
