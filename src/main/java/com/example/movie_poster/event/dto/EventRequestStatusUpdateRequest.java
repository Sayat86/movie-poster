package com.example.movie_poster.event.dto;

import com.example.movie_poster.request.RequestState;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateRequest {
    @NotNull
    private List<Integer> requestIds;
    @NotNull
    private RequestState status;
}
