package com.example.crud.todo.crud.service;

import java.util.List;

import com.example.crud.todo.crud.dto.PublicationDTO;
import com.example.crud.todo.crud.dto.PublicationResponse;

public interface PublicationService {
    
    Object getAllPublications = null;

    public PublicationDTO publicationCreate(PublicationDTO publicationDTO);

    public PublicationResponse getAllPublications(int pageNo,int pageSize);

    public PublicationDTO getPublicationById(long id);
    
    public PublicationDTO updatePublication(PublicationDTO publicationDTO, long id);

    public void deletePublication(long id);
}
