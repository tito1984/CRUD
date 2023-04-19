package com.example.crud.todo.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crud.todo.crud.entities.Publication;

public interface PublicationRepository extends JpaRepository<Publication,Long> {
    
}
