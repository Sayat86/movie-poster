package com.example.movie_poster.event.dto;

import com.example.movie_poster.event.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventAdminRequest {
    private String title;
    private String annotation;
    private String description;
    private String eventDate;
    private Long category;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private Location location;
    private String stateAction; // PUBLISH_EVENT, REJECT_EVENT (по спецификации)
}
