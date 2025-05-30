package com.example.eventsdiscovery.compilation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
    Optional<Compilation> findByTitle(String title);

    Page<Compilation> findByPinned(Boolean pinned, Pageable pageable);
}
