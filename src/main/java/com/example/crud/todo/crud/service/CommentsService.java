package com.example.crud.todo.crud.service;

import com.example.crud.todo.crud.dto.CommentsDTO;

public interface CommentsService {
    
    public CommentsDTO createComment(long publicationId, CommentsDTO commentsDTO);
}
