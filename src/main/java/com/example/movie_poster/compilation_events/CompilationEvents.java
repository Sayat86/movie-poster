package com.example.movie_poster.compilation_events;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "compilation_events")
public class CompilationEvents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
