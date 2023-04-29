package com.example.crud.todo.crud.controlller;

import static org.mockito.Answers.CALLS_REAL_METHODS;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.crud.todo.crud.controller.CommentsController;
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
    void testCommentsListByPublicationId() throws Exception{
        long publicationId = 1L;
        when(commentsService.getCommentsByPublicationId(publicationId)).thenReturn(Arrays.asList(commentsDTO));

        ResultActions response = mockMvc.perform(get("/api/publications/{publicationId}/comments",publicationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentsDTO)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(Arrays.asList(commentsDTO).size())));

    }

    @Test
    void testDeleteComment() throws Exception{
        long commentId = 1L;
        long publicationId = 1L;

        willDoNothing().given(commentsService).deleteComment(publicationId,commentId);

        ResultActions response = mockMvc.perform(delete("/api/publications/{publicationId}/comments/{commentId}",publicationId,commentId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testGetCommentByIdFromPublicationId() throws Exception {
        long publicationId = 1L;

        when(commentsService.getCommentsByPublicationId(publicationId)).thenReturn(Arrays.asList(commentsDTO));

        ResultActions response = mockMvc.perform(get("/api/publications/{publicationId}/comments",publicationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentsDTO)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",is(Arrays.asList(commentsDTO).size())));
    }

    @Test
    void testSaveComment() throws Exception{
        long publicationId = 1L;
        when(commentsService.createComment(publicationId, commentsDTO)).thenReturn(commentsDTO);

        ResultActions response = mockMvc.perform(post("/api/publications/{publicationId}/comments", publicationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentsDTO)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",is(commentsDTO.getName())))
                .andExpect(jsonPath("$.email",is(commentsDTO.getEmail())))
                .andExpect(jsonPath("$.body",is(commentsDTO.getBody())));

    }

    @Test
    void testUpdateComment() throws Exception{
        Long publicationId = 1L;
        Long commentId = 1L;

        when(commentsService.updateComment(publicationId, commentId, commentsDTO)).thenReturn(commentsDTO);

        ResultActions response = mockMvc.perform(put("/api/publications/{publicationId}/comments/{commentId}",publicationId,commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentsDTO)));
              

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(comments.getName())))
                .andExpect(jsonPath("$.email",is(comments.getEmail())))
                .andExpect(jsonPath("$.body",is(comments.getBody())));
    }
}
