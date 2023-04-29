package com.example.crud.todo.crud.controlller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;

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

import com.example.crud.todo.crud.dto.PublicationDTO;
import com.example.crud.todo.crud.dto.PublicationResponse;
import com.example.crud.todo.crud.entities.Publication;
import com.example.crud.todo.crud.service.PublicationService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;;

@WebMvcTest(controllers = PublicationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PublicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicationService publicationService;

    @Autowired
    private ObjectMapper objectMapper;

    private Publication publication;
    private PublicationDTO publicationDTO;

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
    }

    @Test
    void testDeletePublication() throws Exception{
        long publicationId = 1L;
        willDoNothing().given(publicationService).deletePublication(publicationId);

        ResultActions response = mockMvc.perform(delete("/api/publications/{id}",publicationId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void testListPublications() throws Exception{

        PublicationResponse publicationResponse = PublicationResponse.builder().pageNumber(1)
                .pageSize(10).lastPage(true).totalElements(10)
                .content(Arrays.asList(publicationDTO)).totalPages(1).build();

        when(publicationService.getAllPublications(1, 10, null, null))
            .thenReturn(publicationResponse);

        ResultActions response = mockMvc.perform(get("/api/publications")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "1")
                .param("pageSize", "10"));

        response.andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void testGetPublicationById() throws Exception {        
        Long publicationId = 1L;
        when(publicationService.getPublicationById(publicationId)).thenReturn(publicationDTO);

        ResultActions response = mockMvc.perform(get("/api/publications/{id}",publicationId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(publicationDTO)));

        response.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title",is(publication.getTitle())))
            .andExpect(jsonPath("$.description",is(publication.getDescription())))
            .andExpect(jsonPath("$.content",is(publication.getContent())));
    }

    @Test
    void testSavePublication() throws JsonProcessingException, Exception {
        
        given(publicationService.publicationCreate(ArgumentMatchers.any()))
            .willAnswer((Invocation) -> Invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/publications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(publicationDTO)));

        response.andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title",is(publication.getTitle())))
            .andExpect(jsonPath("$.description",is(publication.getDescription())))
            .andExpect(jsonPath("$.content",is(publication.getContent())));
        }

    @Test
    void testUpdatePublication() throws Exception{
        long publicationId = 1L;

        PublicationDTO publication2 = PublicationDTO.builder()
            .title("Nueva publicacion2")
            .description("Publicacion nueva2")
            .content("Esta es la publicacion de la que hablamos2")
            .build();
        given(publicationService.getPublicationById(publicationId)).willReturn(publicationDTO);
        given(publicationService.updatePublication(any(PublicationDTO.class),eq(publicationId)))
                .willAnswer((Invocation) -> Invocation.getArgument(0));

        ResultActions response = mockMvc.perform(put("/api/publications/{id}", publicationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(publication2)));

                response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title",is(publication2.getTitle())))
                .andExpect(jsonPath("$.description",is(publication2.getDescription())))
                .andExpect(jsonPath("$.content",is(publication2.getContent())));
        
    }
}
