package com.example.eventsdiscovery.user;

import com.example.eventsdiscovery.event.Event;
import com.example.eventsdiscovery.request.Request;
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
