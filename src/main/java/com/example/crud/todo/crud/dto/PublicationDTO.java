package com.example.crud.todo.crud.dto;

import java.util.Set;

import com.example.crud.todo.crud.entities.Comments;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public class PublicationDTO {

    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Title must have at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Description must have at least 10 characters")
    private String description;

    @NotEmpty
    @Size(min = 10, message = "Content must have at least 10 characters")
    private String content;

    private Set<Comments> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PublicationDTO() {
        super();
    }

    public Set<Comments> getComments() {
        return comments;
    }

    public void setComments(Set<Comments> comments) {
        this.comments = comments;
    }

    @Builder
    public PublicationDTO(@NotEmpty @Size(min = 2, message = "Title must have at least 2 characters") String title,
            @NotEmpty @Size(min = 10, message = "Description must have at least 10 characters") String description,
            @NotEmpty @Size(min = 10, message = "Content must have at least 10 characters") String content) {
        this.title = title;
        this.description = description;
        this.content = content;
    }

}
