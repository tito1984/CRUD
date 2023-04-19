package com.example.crud.todo.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crud.todo.crud.dto.CommentsDTO;
import com.example.crud.todo.crud.entities.Comments;
import com.example.crud.todo.crud.entities.Publication;
import com.example.crud.todo.crud.exceptions.ResourceNotFoundException;
import com.example.crud.todo.crud.repository.CommentRepository;
import com.example.crud.todo.crud.repository.PublicationRepository;

@Service
public class CommentsServiceImpl implements CommentsService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    @Override
    public CommentsDTO createComment(long publicationId, CommentsDTO commentsDTO) {
        Comments comments = mapEntity(commentsDTO);
        Publication publication = publicationRepository
                .findById(publicationId).orElseThrow(() -> new ResourceNotFoundException("Publication", "id", publicationId));

        comments.setPublication(publication);
        Comments newComments = commentRepository.save(comments);

        return mapDTO(newComments);
    }
    
    private CommentsDTO mapDTO(Comments comments){
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setId(comments.getId());
        commentsDTO.setName(comments.getName());
        commentsDTO.setEmail(comments.getEmail());
        commentsDTO.setBody(comments.getBody());

        return commentsDTO;
    }

    private Comments mapEntity(CommentsDTO commentsDTO) {
        Comments comments = new Comments();
        comments.setId(commentsDTO.getId());
        comments.setName(commentsDTO.getName());
        comments.setEmail(commentsDTO.getEmail());
        comments.setBody(commentsDTO.getBody());

        return comments;        
    }
}
