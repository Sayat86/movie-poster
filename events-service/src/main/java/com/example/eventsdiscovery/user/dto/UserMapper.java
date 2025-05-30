package com.example.eventsdiscovery.user.dto;

import com.example.eventsdiscovery.user.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public User fromCreate(UserCreateDto userCreate) {
        User user = new User();
        user.setEmail(userCreate.getEmail());
        user.setName(userCreate.getName());
        return user;
    }

    public User fromUpdate(UserUpdateDto userUpdate) {
        User user = new User();
        user.setEmail(userUpdate.getEmail());
        user.setName(userUpdate.getName());
        return user;
    }

    public UserResponseDto toResponse(User user) {
        UserResponseDto userResponse = new UserResponseDto();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }

    public List<UserResponseDto> toResponse(List<User> users) {
        return users.stream()
                .map(this::toResponse)
                .toList();
    }

    public void merge(User existingUser, User updateUser) {
        if (updateUser.getEmail() != null && !updateUser.getEmail().isBlank()) {
            existingUser.setEmail(updateUser.getEmail());
        }

        if (updateUser.getName() != null && !updateUser.getName().isBlank()) {
            existingUser.setName(updateUser.getName());
        }
    }
}
