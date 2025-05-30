package com.example.eventsdiscovery.compilation;

import java.util.List;

public interface CompilationService {
    List<Compilation> findAll(Boolean pinned, int page, int size);
    Compilation findById(int id);
    Compilation create(Compilation compilation);
    void deleteById(int id);
    Compilation update(Compilation compilation, int id);
}
