package com.example.crud.todo.crud.controlller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/publication/{publicationId}/comments")
    public ResponseEntity<CommentsDTO> saveComment(
            @PathVariable(value = "publicationId") long publicationId, @RequestBody CommentsDTO commentsDTO){
        return new ResponseEntity<>(commentsService.createComment(publicationId,commentsDTO),HttpStatus.CREATED);
    }
}
