package com.example.crud.todo.crud.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.crud.todo.crud.entities.Publication;

// TODO LIST
// PublicationCreate
// -Comprobar que el guarda en la base de datos
// GetAllPublications
// -Comprobar que te devuelve todos los elementos de la lista
// getPublicationById
// -Comprobar que te devuelve la publicacion del id dado
// updatePublication
// -Comprobar que se modifica toda la publicacion correctamente
// deletePublication
// -Comprobar que se borra el comentario del id dado

@DataJpaTest
public class PublicationRepositoryTest {

    @Autowired
    private PublicationRepository publicationRepository;

    private Publication publication;

    @BeforeEach
    void init() {
        publication = Publication.builder()
                .title("Nueva publicacion")
                .description("Publicacion nueva")
                .content("Esta es la publicacion de la que hablamos")
                .build();
    }

    @Test
    void publicationCreateTest() {

        Publication savedPublication = publicationRepository.save(publication);

        assertThat(savedPublication.getTitle()).isNotNull();
        assertThat(savedPublication.getId()).isGreaterThan(0);
    }

    @Test
    void getAllPublicationsTest() {

        Publication publication1 = Publication.builder()
                .title("Esto es otra prueba")
                .description("Publicacion nueva")
                .content("Esta es la publicacion de la que hablamos")
                .build();

        publicationRepository.save(publication1);
        publicationRepository.save(publication);

        List<Publication> publicationList = publicationRepository.findAll();

        assertThat(publicationList).isNotNull();
        assertThat(publicationList.size()).isEqualTo(2);

    }

    @Test
    void getPublicationByIdTest() {

        publicationRepository.save(publication);

        Publication publicationById = publicationRepository.findById(publication.getId()).get();

        assertThat(publicationById).isNotNull();
    }

    @Test
    void updatePublicationTest() {
        
        publicationRepository.save(publication);

        Publication savedPublication = publicationRepository.findById(publication.getId()).get();
        savedPublication.setTitle("Nuevo nombre");
        savedPublication.setDescription("Estoy modificando");
        savedPublication.setContent("Esto seria el contenido");

        Publication updatedPublication = publicationRepository.save(savedPublication);

        assertThat(updatedPublication.getTitle()).isEqualTo("Nuevo nombre");
        assertThat(updatedPublication.getDescription()).isEqualTo("Estoy modificando");
        assertThat(updatedPublication.getContent()).isEqualTo("Esto seria el contenido");

    }

    @Test
    void deletePublicationTest() {

        publicationRepository.save(publication);

        publicationRepository.deleteById(publication.getId());

        Optional<Publication> publicationOptional = publicationRepository.findById(publication.getId());

        assertThat(publicationOptional).isEmpty();
    }
}
