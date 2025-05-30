package com.example.eventsdiscovery.user;

import java.util.List;

public interface UserService {
    User create(User user);
    User update(User user, int id);
    User findById(int id);
    void deleteById(int id);
    List<User> findAll(List<Integer> ids, int page, int size);
    //List<User> findAllEventsByUserId(int id, int page, int size);
}
