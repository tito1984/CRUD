package com.example.crud.todo.crud.service;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.crud.todo.crud.dto.CommentsDTO;
import com.example.crud.todo.crud.dto.PublicationDTO;
import com.example.crud.todo.crud.entities.Comments;
import com.example.crud.todo.crud.entities.Publication;
import com.example.crud.todo.crud.repository.CommentRepository;
import com.example.crud.todo.crud.repository.PublicationRepository;

@ExtendWith(MockitoExtension.class)
public class CommentsServiceImplTest {
    
    @Mock
    CommentRepository commentRepository;

    @Mock
    PublicationRepository publicationRepository;
    
    @InjectMocks
    CommentsService commentsService = new CommentsServiceImpl();

    private Publication publication;
    private PublicationDTO publicationDTO;
    private Comments comments;
    private CommentsDTO commentsDTO;

    @BeforeEach
    public void init() {
        publication = Publication.builder()
                .id(1L)
                .title("esto es una publicacion")
                .description("Esta es la descripcion")
                .content("Eto es el contenido")
                .build();
        publicationDTO =PublicationDTO.builder()
                .title("esto es una publicacion")
                .description("Esta es la descripcion")
                .content("Eto es el contenido")
                .build();
        comments = Comments.builder()
                .name("Nombre")
                .email("hola@hola.com")
                .body("Esto es un comentario de prueba")
                .publication(publication)
                .build();

        commentsDTO = CommentsDTO.builder()
                .name("Nombre")
                .email("hola@hola.com")
                .body("Esto es un comentario de prueba")
                .build();
        
    }


    @Test
    void testCreateComment() {
        
        when(publicationRepository.findById(publication.getId())).thenReturn(Optional.of(publication));

        when(commentRepository.save(Mockito.any(Comments.class))).thenReturn(comments);

        CommentsDTO savedComment = commentsService.createComment(publication.getId(), commentsDTO);

        assertThat(savedComment).isNotNull();
    }

    @Test
    void testDeleteComment() {
        Long publicationId = 1L;
        Long commentId =1L;

        publication.setComments(Set.of(comments));
        comments.setPublication(publication);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comments));
        when(publicationRepository.findById(publicationId)).thenReturn(Optional.of(publication));
        
        assertAll(() -> commentsService.deleteComment(publicationId, commentId));
    }

    @Test
    void testGetCommentsById() {
        Long publicationId = 1L;
        Long commentId =1L;

        comments.setPublication(publication);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comments));
        when(publicationRepository.findById(publicationId)).thenReturn(Optional.of(publication));

        CommentsDTO commentReturn = commentsService.getCommentsById(publicationId, commentId);

        assertThat(commentReturn).isNotNull();
    }

    @Test
    void testGetCommentsByPublicationId() {
        Long publicationId = 1L;

        when(commentRepository.findByPublicationId(publicationId)).thenReturn(Arrays.asList(comments));

        List<CommentsDTO> commentsReturn = commentsService.getCommentsByPublicationId(publicationId);

        assertThat(commentsReturn).isNotNull();
    }

    @Test
    void testUpdateComment() {
        Long publicationId = 1L;
        Long commentId = 1L;

        publication.setComments(Set.of(comments));
        comments.setPublication(publication);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comments));
        when(publicationRepository.findById(publicationId)).thenReturn(Optional.of(publication));
        when(commentRepository.save(comments)).thenReturn(comments);

        CommentsDTO updateComments = commentsService.updateComment(publicationId, commentId, commentsDTO);

        assertThat(updateComments).isNotNull();
    }
}
