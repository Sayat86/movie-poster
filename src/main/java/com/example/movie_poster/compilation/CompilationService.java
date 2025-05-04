package com.example.movie_poster.compilation;

import java.util.List;

public interface CompilationService {
    List<Compilation> findAll();
    Compilation findById(int id);
    Compilation create(Compilation compilation);
    void deleteById(int id);
    Compilation update(Compilation compilation, int id);
}
