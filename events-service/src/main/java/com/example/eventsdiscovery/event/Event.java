package com.example.eventsdiscovery.event;

import com.example.eventsdiscovery.category.Category;
import com.example.eventsdiscovery.request.Request;
import com.example.eventsdiscovery.user.User;
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
    private Integer id;
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
    private Integer participantLimit;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Enumerated(value = EnumType.STRING)
    private EventState state;
    @Embedded
    @AttributeOverride(name = "lat", column = @Column(name = "location_lat"))
    @AttributeOverride(name = "lon", column = @Column(name = "location_lon"))
    private Location location;
    @Column(name = "confirmed_requests")
    private Integer confirmedRequests = 0;
    private Integer views;
    @Column(name = "is_published")
    private Boolean isPublished;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @OneToMany(mappedBy = "event")
    private List<Request> requests;
}
