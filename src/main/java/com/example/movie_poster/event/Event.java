package com.example.movie_poster.event;

import com.example.movie_poster.category.Category;
import com.example.movie_poster.compilation_events.CompilationEvents;
import com.example.movie_poster.request.Request;
import com.example.movie_poster.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String annotation;
    private String description;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    private Boolean paid;
    @Column(name = "participant_limit")
    private int participantLimit;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    private String state;
    @Column(name = "location_lat")
    private Double locationLat;
    @Column(name = "location_lon")
    private Double locationLon;
    @Column(name = "confirmed_requests")
    private int confirmedRequests;
    private int views;
    @Column(name = "is_published")
    private Boolean isPublished;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @OneToMany(mappedBy = "event")
    private List<CompilationEvents> compilationEvents;

    @OneToMany(mappedBy = "event")
    private List<Request> requests;
}
