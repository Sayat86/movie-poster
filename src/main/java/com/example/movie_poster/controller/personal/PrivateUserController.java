package com.example.movie_poster.controller.personal;

import com.example.movie_poster.user.UserService;
import com.example.movie_poster.user.dto.UserMapper;
import com.example.movie_poster.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.movie_poster.utils.RequestConstants.DEFAULT_FROM;
import static com.example.movie_poster.utils.RequestConstants.DEFAULT_SIZE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class PrivateUserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public UserResponseDto findById(@PathVariable int id) {
        return userMapper.toResponse(userService.findById(id));
    }
}
