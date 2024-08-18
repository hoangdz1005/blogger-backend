package com.bloggerbackend.repositories.mysql;

import com.bloggerbackend.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByEmail(String email);
}
