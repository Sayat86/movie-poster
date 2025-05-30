package com.example.eventsdiscovery.controller.personal;

import com.example.eventsdiscovery.user.UserService;
import com.example.eventsdiscovery.user.dto.UserMapper;
import com.example.eventsdiscovery.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
