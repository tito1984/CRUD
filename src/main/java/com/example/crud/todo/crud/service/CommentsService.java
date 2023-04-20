package com.example.crud.todo.crud.service;

import java.util.List;

import com.example.crud.todo.crud.dto.CommentsDTO;

public interface CommentsService {
    
    public CommentsDTO createComment(long publicationId, CommentsDTO commentsDTO);

    public List<CommentsDTO> getCommentsByPublicationId(long publicationId);

    public CommentsDTO getCommentsById(Long publicationId, Long commentId);

    public CommentsDTO updateComment(Long publicationId,Long commentId, CommentsDTO applicationComment);

    public void deleteComment(Long publicationId,Long commentId);
}
