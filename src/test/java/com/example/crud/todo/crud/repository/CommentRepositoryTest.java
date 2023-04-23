package com.example.crud.todo.crud.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.crud.todo.crud.entities.Comments;
import com.example.crud.todo.crud.entities.Publication;

// TODO LIST
// Create comment
// -Comprobar que se guarda el comentario
// -Comprobar que lo guarda con el id correcto
// GetCommentsByPublicationId
// -Comprobar que te devuelve todos los elementos de la lista
// -Comprobar que te devuelve los elementos del id de publicacion dado
// GetCommentsById
// -Comprobar que te devuelve el comentario del id dado
// UpdateComment
// -Comprobar que se modifica el comentario del id dado correctamente
// DeleteComment
// -Comprobar que se borra el comentario del id dado


@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    private Publication publication;

    private Comments comments;

    @BeforeEach
    void initPublication() {
        publication = Publication.builder()
                .title("Nueva publicacion")
                .description("Publicacion nueva")
                .content("Esta es la publicacion de la que hablamos")
                .build();
    }

    @BeforeEach
    void initComment() {
        comments = Comments.builder()
            .name("Esto es una publicacion")
            .email("hola@gmail.com")
            .body("Este es un comentario simple")
            .publication(publication)
            .build();
    }

    @Test
    void createCommentTest(){
        Publication savedPublication = publicationRepository.save(publication);

        comments.setPublication(savedPublication);
        Comments savedComments = commentRepository.save(comments);
        
        assertThat(savedComments).isNotNull();
        assertThat(savedComments.getId()).isGreaterThan(0);

    }

    @Test
    void getCommentsByPublicationIdTest() {
        Publication savedPublication = publicationRepository.save(publication);

        comments.setPublication(savedPublication);
        commentRepository.save(comments);
        Comments comments2 = Comments.builder()
            .name("Esto es una publicacion 2")
            .email("hola2@gmail.com")
            .body("Este es un comentario simple 2")
            .publication(publication)
            .build();
        commentRepository.save(comments2);

        List<Comments> commentsByPublicationIdList = commentRepository.findByPublicationId(savedPublication.getId());

        assertThat(commentsByPublicationIdList).isNotNull();
        assertThat(commentsByPublicationIdList.size()).isEqualTo(2);
    }

    @Test
    void getCommentsByIdTest() {
        Publication savedPublication = publicationRepository.save(publication);

        comments.setPublication(savedPublication);
        Comments savedComments = commentRepository.save(comments);

        Comments commentsById = commentRepository.findById(savedComments.getId()).get();

        assertThat(commentsById.getId()).isNotNull();
    }

    @Test
    void updateCommentTest() {
        Publication savedPublication = publicationRepository.save(publication);

        comments.setPublication(savedPublication);
        commentRepository.save(comments);

        Comments savedComments = commentRepository.findById(comments.getId()).get();
        savedComments.setName("Este es el nuevo nombre");
        savedComments.setEmail("este@este.com");
        savedComments.setBody("El comentario guardado nuevo");
        
        Comments updatedComments = commentRepository.save(savedComments);

        assertThat(updatedComments.getName()).isEqualTo("Este es el nuevo nombre");
        assertThat(updatedComments.getEmail()).isEqualTo("este@este.com");
        assertThat(updatedComments.getBody()).isEqualTo("El comentario guardado nuevo");
    }

    @Test
    void deleteCommentTest() {
        Publication savedPublication = publicationRepository.save(publication);

        comments.setPublication(savedPublication);
        commentRepository.save(comments);

        commentRepository.deleteById(comments.getId());

        Optional<Comments> commentsOptional = commentRepository.findById((comments.getId()));

        assertThat(commentsOptional).isEmpty();
    }
}
