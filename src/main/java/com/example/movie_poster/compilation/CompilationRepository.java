package com.example.movie_poster.compilation;

import com.example.movie_poster.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
    Optional<Compilation> findByTitle(String title);
}
