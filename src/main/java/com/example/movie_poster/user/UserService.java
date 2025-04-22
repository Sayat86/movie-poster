package com.example.movie_poster.user;

import java.util.List;

public interface UserService {
    User create(User user);
    User update(User user, int id);
    User findById(int id);
    void deleteById(int id);
    List<User> findAll();
}
