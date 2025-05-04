package com.example.movie_poster.request;

import java.util.List;

public interface RequestService {
    Request create(int userId, int eventId);
    Request update(int userId, int requestId);
    List<Request> findAll(int userId);
}
