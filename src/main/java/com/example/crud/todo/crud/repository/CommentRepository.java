package com.example.crud.todo.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crud.todo.crud.entities.Comments;

public interface CommentRepository extends JpaRepository<Comments,Long>{
    
}
