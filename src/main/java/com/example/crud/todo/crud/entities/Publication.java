package com.example.crud.todo.crud.entities;

import java.util.HashSet;
import java.util.Set;

import com.example.crud.todo.crud.dto.PublicationDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;


@Entity
@Table(name = "publications", uniqueConstraints = { @UniqueConstraint(columnNames = { "title" }) })
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;

    @JsonBackReference
    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Comments> comments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Set<Comments> getComments() {
        return comments;
    }

    public void setComments(Set<Comments> comments) {
        this.comments = comments;
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

    public Publication() {
        super();
    }

    @Builder
    public Publication(Long id, String title, String description, String content) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
    }

    public PublicationDTO mapEntity(){
        PublicationDTO publicationDTO = new PublicationDTO();
        publicationDTO.setId(this.id);
        publicationDTO.setTitle(this.title);
        publicationDTO.setDescription(this.description);
        publicationDTO.setContent(this.content);
        publicationDTO.setComments(this.comments);

        return publicationDTO;
    }

    public Publication mapDTO(PublicationDTO publicationDTO){        
        this.setTitle(publicationDTO.getTitle());
        this.setDescription(publicationDTO.getDescription());
        this.setContent(publicationDTO.getContent());
        return this;
    }
    
}
