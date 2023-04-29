package com.example.crud.todo.crud.service;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.crud.todo.crud.dto.PublicationDTO;
import com.example.crud.todo.crud.dto.PublicationResponse;
import com.example.crud.todo.crud.entities.Publication;
import com.example.crud.todo.crud.repository.PublicationRepository;

@ExtendWith(MockitoExtension.class)
public class PublicationServiceImplTest {

    @Mock
    private PublicationRepository publicationRepository;

    @InjectMocks
    private PublicationServiceImpl publicationService;

    
    @Test
    void testDeletePublication() {
        Publication publication = Publication.builder()
                .title("esto es una publicacion")
                .description("Esta es la descripcion")
                .content("Eto es el contenido")
                .build();


        when(publicationRepository.findById(1L)).thenReturn(Optional.ofNullable(publication));


        assertAll(()->publicationService.deletePublication(1));
    }

    @Test
    void testGetAllPublications() {
        PublicationResponse publicationResponse = Mockito.mock(PublicationResponse.class);
        Page<Publication> publications = Mockito.mock(Page.class);

        when(publicationRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(publications);

        PublicationResponse savedPublications = publicationService.getAllPublications(1, 10, "id", "asc");

        assertThat(savedPublications).isNotNull();
    }

    @Test
    void testGetPublicationById() {
        Publication publication = Publication.builder()
                .title("esto es una publicacion")
                .description("Esta es la descripcion")
                .content("Eto es el contenido")
                .build();
        

        when(publicationRepository.findById(1L)).thenReturn(Optional.ofNullable(publication));

        PublicationDTO savedPublication = publicationService.getPublicationById(1L);
        

        assertThat(savedPublication).isNotNull();
    }

    @Test
    void testPublicationCreate() {
        Publication publication = Publication.builder()
                .title("esto es una publicacion")
                .description("Esta es la descripcion")
                .content("Eto es el contenido")
                .build();
        PublicationDTO publicationDTO =PublicationDTO.builder()
                .title("esto es una publicacion")
                .description("Esta es la descripcion")
                .content("Eto es el contenido")
                .build();
        
        when(publicationRepository.save(Mockito.any(Publication.class))).thenReturn(publication);

        PublicationDTO savedPublication = publicationService.publicationCreate(publicationDTO);
        

        assertThat(savedPublication).isNotNull();
    }

    @Test
    void testUpdatePublication() {
        Publication publication = Publication.builder()
                .title("esto es una publicacion")
                .description("Esta es la descripcion")
                .content("Eto es el contenido")
                .build();
        PublicationDTO publicationDTO =PublicationDTO.builder()
                .title("esto es una publicacion")
                .description("Esta es la descripcion")
                .content("Eto es el contenido")
                .build();

        when(publicationRepository.findById(1L)).thenReturn(Optional.ofNullable(publication));
        when(publicationRepository.save(Mockito.any(Publication.class))).thenReturn(publication);

        PublicationDTO savedPublication = publicationService.updatePublication(publicationDTO, 1);


        assertThat(savedPublication).isNotNull();                                           
    }
}
