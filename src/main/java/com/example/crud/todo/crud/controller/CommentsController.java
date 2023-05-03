package com.example.crud.todo.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.todo.crud.dto.CommentsDTO;
import com.example.crud.todo.crud.exceptions.BlogAppException;
import com.example.crud.todo.crud.exceptions.ResourceNotFoundException;
import com.example.crud.todo.crud.service.CommentsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/publications")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @GetMapping("/{publicationId}/comments")
    public ResponseEntity<List<CommentsDTO>> commentsListByPublicationId(
            @PathVariable(value = "publicationId") long publicationId) {
        try {
            return ResponseEntity.ok(commentsService.getCommentsByPublicationId(publicationId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(commentsService.getCommentsByPublicationId(publicationId));
        }
    }

    @GetMapping("/{publicationId}/comments/{commentId}")
    public ResponseEntity<CommentsDTO> getCommentByIdFromPublicationId(
            @PathVariable(value = "publicationId") long publicationId,
            @PathVariable(value = "commentId") long commentId) {

        try {
            return new ResponseEntity<CommentsDTO>(commentsService.getCommentsById(publicationId, commentId),
                    HttpStatus.OK);
        } catch (BlogAppException e) {
            return new ResponseEntity<CommentsDTO>(commentsService.getCommentsById(publicationId, commentId),
                    HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<CommentsDTO>(commentsService.getCommentsById(publicationId, commentId),
                    HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/{publicationId}/comments")
    public ResponseEntity<CommentsDTO> createComment(
            @Valid @PathVariable(value = "publicationId") long publicationId, @RequestBody CommentsDTO commentsDTO) { 
        CommentsDTO result = new CommentsDTO();
        try {
            result = commentsService.createComment(publicationId, commentsDTO);
            return new ResponseEntity<CommentsDTO>(result,
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<CommentsDTO>(result,
                    HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{publicationId}/comments/{commentId}")
    public ResponseEntity<CommentsDTO> updateComment(
            @PathVariable(value = "publicationId") long publicationId,
            @PathVariable(value = "commentId") long commentId,
            @Valid @RequestBody CommentsDTO commentsDTO) {
        try {
            return new ResponseEntity<CommentsDTO>(commentsService.updateComment(publicationId, commentId, commentsDTO),
                    HttpStatus.OK);
        } catch (BlogAppException e) {
            return new ResponseEntity<CommentsDTO>(commentsService.updateComment(publicationId, commentId, commentsDTO),
                    HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<CommentsDTO>(commentsService.updateComment(publicationId, commentId, commentsDTO),
                    HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{publicationId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable(value = "publicationId") long publicationId,
            @PathVariable(value = "commentId") long commentId) {

        try {
            commentsService.deleteComment(publicationId, commentId);
            return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
        } catch (BlogAppException e) {
            return new ResponseEntity<>("Comment not belongs to that publication", HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("Comment or publication not found", HttpStatus.NOT_FOUND);
        }

    }
}
