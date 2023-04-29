package com.example.crud.todo.crud.controlller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.crud.todo.crud.dto.CommentsDTO;
import com.example.crud.todo.crud.dto.PublicationDTO;
import com.example.crud.todo.crud.entities.Comments;
import com.example.crud.todo.crud.entities.Publication;
import com.example.crud.todo.crud.service.CommentsService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = CommentsController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommentsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentsService commentsService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void testCommentsListByPublicationId() {

    }

    @Test
    void testDeleteComment() {

    }

    @Test
    void testGetCommentByIdFromPublicationId() throws Exception {
        Long publicationId = 1L;

        when(commentsService.getCommentsByPublicationId(publicationId)).thenReturn(Arrays.asList(commentsDTO));

        ResultActions response = mockMvc.perform(get("/publication/{publicationId}/comments",publicationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentsDTO)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",CoreMatchers.is(Arrays.asList(commentsDTO).size())));
    }

    @Test
    void testSaveComment() throws Exception{
        Long publicationId = 1L;
        given(commentsService.createComment(publicationId, commentsDTO)).willReturn(commentsDTO);

        ResultActions response = mockMvc.perform(post("/publication/{publicationId}/comments", publicationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentsDTO)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(comments.getName())))
                .andExpect(jsonPath("$.email",is(comments.getEmail())))
                .andExpect(jsonPath("$.body",is(comments.getBody())));

    }

    @Test
    void testUpdateComment() {

    }
}
