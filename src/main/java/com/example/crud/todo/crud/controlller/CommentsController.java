package com.example.crud.todo.crud.controlller;

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
import com.example.crud.todo.crud.service.CommentsService;

@RestController
@RequestMapping("/api/")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @GetMapping("/publication/{publicationId}/comments")
    public List<CommentsDTO> commentsListByPublicationId(@PathVariable(value = "publicationId") long publicationId) {
        return commentsService.getCommentsByPublicationId(publicationId);
    }

    @GetMapping("/publication/{publicationId}/comments/{commentId}")
    public ResponseEntity<CommentsDTO> getCommentByIdFromPublicationId(
            @PathVariable(value = "publicationId") long publicationId,
            @PathVariable(value = "commentId") long commentId) {

        CommentsDTO commentsDTO = commentsService.getCommentsById(publicationId, commentId);

        return new ResponseEntity<>(commentsDTO, HttpStatus.OK);

    }

    @PostMapping("/publication/{publicationId}/comments")
    public ResponseEntity<CommentsDTO> saveComment(
            @PathVariable(value = "publicationId") long publicationId, @RequestBody CommentsDTO commentsDTO) {
        return new ResponseEntity<>(commentsService.createComment(publicationId, commentsDTO), HttpStatus.CREATED);
    }

    @PutMapping("/publication/{publicationId}/comments/{commentId}")
    public ResponseEntity<CommentsDTO> updateComment(
            @PathVariable(value = "publicationId") long publicationId, @PathVariable(value = "commentId") long commentId,
            @RequestBody CommentsDTO commentsDTO) {
        
        CommentsDTO commentUdated = commentsService.updateComment(publicationId, commentId, commentsDTO);
        return new ResponseEntity<>(commentUdated,HttpStatus.OK);
    }


    @DeleteMapping("/publication/{publicationId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
        @PathVariable(value = "publicationId") long publicationId,
        @PathVariable(value = "commentId") long commentId) {

            commentsService.deleteComment(publicationId,commentId);

            return new ResponseEntity<>("Comment deleted successfully",HttpStatus.OK);
        }
}
