package com.example.movie_poster.request;

import java.sql.Timestamp;
import java.util.List;

public class ApiError {
    private List<String> errors;
    private String message;
    private String reason;
    private String status;
    private Timestamp timestamp;
}
