package com.example.movie_poster.compilation;

import com.example.movie_poster.compilation_events.CompilationEvents;
import com.example.movie_poster.event.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //подборка
    private String title;
    private Boolean pinned;

    @OneToMany(mappedBy = "compilation", cascade = CascadeType.ALL)
    private List<CompilationEvents> compilationEvents;
}
