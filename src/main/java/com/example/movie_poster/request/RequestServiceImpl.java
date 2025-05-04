package com.example.movie_poster.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;

    @Override
    public Request create(int userId, int eventId) {
        return null;
    }

    @Override
    public Request update(int userId, int requestId) {
        return null;
    }

    @Override
    public List<Request> findAll(int userId) {
        return List.of();
    }
}
