package com.example.crud.todo.crud.entities;

import com.example.crud.todo.crud.dto.CommentsDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "comments")
public class Comments {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String email;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id",nullable = false)
    private Publication publication;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    @Builder
    public Comments(long id, String name, String email, String body, Publication publication) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
        this.publication = publication;
    }

    public Comments() {
        super();
    }

    public CommentsDTO mapEntity(){
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setName(this.name);
        commentsDTO.setEmail(this.email);
        commentsDTO.setBody(this.body);

        return commentsDTO;
    }

    public Comments mapDTO(CommentsDTO commentsDTO){
        Comments comments = new Comments();
        comments.setName(commentsDTO.getName());
        comments.setEmail(commentsDTO.getEmail());
        comments.setBody(commentsDTO.getBody());

        return comments;
    }
}
