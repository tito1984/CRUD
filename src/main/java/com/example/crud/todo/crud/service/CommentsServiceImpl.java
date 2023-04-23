package com.example.crud.todo.crud.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.crud.todo.crud.dto.CommentsDTO;
import com.example.crud.todo.crud.entities.Comments;
import com.example.crud.todo.crud.entities.Publication;
import com.example.crud.todo.crud.exceptions.BlogAppException;
import com.example.crud.todo.crud.exceptions.ResourceNotFoundException;
import com.example.crud.todo.crud.repository.CommentRepository;
import com.example.crud.todo.crud.repository.PublicationRepository;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    @Override
    public CommentsDTO createComment(long publicationId, CommentsDTO commentsDTO) {
        Comments comments = new Comments();
        comments.mapDTO(commentsDTO);
        Publication publication = publicationRepository
                .findById(publicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Publication", "id", publicationId));

        comments.setPublication(publication);
        Comments newComments = commentRepository.save(comments);

        return newComments.mapEntity();
    }

    @Override
    public List<CommentsDTO> getCommentsByPublicationId(long publicationId) {
        List<Comments> comments = commentRepository.findByPublicationId(publicationId);

        return comments.stream().map(comment -> comment.mapEntity()).collect(Collectors.toList());
    }

    @Override
    public CommentsDTO getCommentsById(Long publicationId, Long commentId) {
        Publication publication = publicationRepository
                .findById(publicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Publication", "id", publicationId));

        Comments comments = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));

        if (!comments.getPublication().getId().equals(publication.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Comment not belongs to that publication");
        }

        return comments.mapEntity();
    }

    @Override
    public CommentsDTO updateComment(Long publicationId, Long commentId, CommentsDTO applicationComment) {
        Publication publication = publicationRepository
                .findById(publicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Publication", "id", publicationId));

        Comments comments = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));

        if (!comments.getPublication().getId().equals(publication.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Comment not belongs to that publication");
        }

        comments.setName(applicationComment.getName());
        comments.setEmail(applicationComment.getEmail());
        comments.setBody(applicationComment.getBody());

        Comments commentUpdate = commentRepository.save(comments);

        return commentUpdate.mapEntity();
    }

    @Override
    public void deleteComment(Long publicationId, Long commentId) {
        Publication publication = publicationRepository
                .findById(publicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Publication", "id", publicationId));

        Comments comments = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));

        if (!comments.getPublication().getId().equals(publication.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Comment not belongs to that publication");
        }

        commentRepository.delete(comments);
    }

}
