package com.example.crud.todo.crud.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CommentsDTO {

    private long id;

    @NotEmpty(message = "Name can not be empty")
    private String name;

    @NotEmpty(message = "Email can not be empty")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 10,message = "Body must have at least 10 character")
    private String body;

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

    public CommentsDTO() {
        super();
    }

}
