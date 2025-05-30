package com.example.eventsdiscovery.event.dto;

import com.example.eventsdiscovery.event.EventStateActionAdmin;
import com.example.eventsdiscovery.event.Location;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventAdminRequest {
    @Size(min = 3, max = 120)
    private String title;

    @Size(min = 20, max = 2000)
    private String annotation;

    @Size(min = 20, max = 7000)
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Integer category;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;

    private Location location;
    private EventStateActionAdmin stateAction; // PUBLISH_EVENT, REJECT_EVENT (по спецификации)
}
