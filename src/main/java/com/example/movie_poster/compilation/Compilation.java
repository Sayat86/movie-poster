package com.example.movie_poster.compilation;

import com.example.movie_poster.compilation_events.CompilationEvents;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private Boolean pinned;

    @OneToMany(mappedBy = "compilation")
    private List<CompilationEvents> compilationEvents;
}
