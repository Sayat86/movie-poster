package com.example.movie_poster.user;

import com.example.movie_poster.event.Event;
import com.example.movie_poster.request.Request;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "initiator")
    private List<Event> events;

    @OneToMany(mappedBy = "requester")
    private List<Request> requests;
}
