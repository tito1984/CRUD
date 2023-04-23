package com.example.crud.todo.crud.controlller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PublicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicationService publicationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testDeletePublication() {

    }

    @Test
    void testListPublications() throws Exception{
        List<PublicationDTO> publicationList = new ArrayList<>();
        publicationList.add(PublicationDTO.builder().title("Nueva publicacion2").description("Publicacion nueva2")
            .content("Esta es la publicacion de la que hablamos2").build());
        publicationList.add(PublicationDTO.builder().title("Nueva publicacion").description("Publicacion nueva")
            .content("Esta es la publicacion de la que hablamos").build());
        publicationList.add(PublicationDTO.builder().title("Nueva publicacion3").description("Publicacion nueva3")
            .content("Esta es la publicacion de la que hablamos3").build());
        PublicationResponse publicationResponse = new PublicationResponse();
        publicationResponse.setContent(publicationList);
        publicationResponse.setPageNumber(0);
        publicationResponse.setPageSize(10);
        publicationResponse.setTotalElements(10);
        publicationResponse.setTotalPages(1);
        publicationResponse.setLastPage(true);

        given(publicationService.getAllPublications(0, 10, null, null))
            .willReturn(publicationResponse);

        ResultActions response = mockMvc.perform(get("/api/publications"));

        response.andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void testGetPublicationById() throws Exception {
        long publicationId = 1L;
        PublicationDTO publicationDTO = PublicationDTO.builder()
            .title("Nueva publicacion")
            .description("Publicacion nueva")
            .content("Esta es la publicacion de la que hablamos")
            .build();
        given(publicationService.getPublicationById(publicationId)).willReturn(Optional.of(publicationDTO).get());

        ResultActions response = mockMvc.perform(get("/api/publications/{id}",publicationId));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.title",is(publicationDTO.getTitle())))
                .andExpect(jsonPath("$.description",is(publicationDTO.getDescription())))
                .andExpect(jsonPath("$.content",is(publicationDTO.getContent())));
    }

    @Test
    void testGetPublicationByIdNotFound() throws Exception {
        long publicationId = 2L;
        PublicationDTO publicationDTO = PublicationDTO.builder()
            .title("Nueva publicacion")
            .description("Publicacion nueva")
            .content("Esta es la publicacion de la que hablamos")
            .build();
        given(publicationService.getPublicationById(publicationId)).willReturn(Optional.ofNullable(publicationDTO).get());

        ResultActions response = mockMvc.perform(get("/api/publications/{id}",publicationId));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void testSavePublication() throws JsonProcessingException, Exception {
        Publication publication = Publication.builder()
            .id(1L)
            .title("Nueva publicacion")
            .description("Publicacion nueva")
            .content("Esta es la publicacion de la que hablamos")
            .build();

        given(publicationService.publicationCreate(any(PublicationDTO.class)))
            .willAnswer((Invocation) -> Invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/publications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(publication)));

        response.andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title",is(publication.getTitle())))
            .andExpect(jsonPath("$.description",is(publication.getDescription())))
            .andExpect(jsonPath("$.content",is(publication.getContent())));
        }

    @Test
    void testUpdatePublication() {

    }
}
