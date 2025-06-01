package com.example.eventsdiscovery.controller.admin;

import com.example.eventsdiscovery.user.User;
import com.example.eventsdiscovery.user.UserService;
import com.example.eventsdiscovery.user.dto.UserCreateDto;
import com.example.eventsdiscovery.user.dto.UserMapper;
import com.example.eventsdiscovery.user.dto.UserResponseDto;
import com.example.eventsdiscovery.user.dto.UserUpdateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto create(@Valid @RequestBody UserCreateDto userCreate) {
        User user = userMapper.fromCreate(userCreate);
        return userMapper.toResponse(userService.create(user));
    }

    @PatchMapping("/{id}")
    public UserResponseDto update(@Valid @RequestBody UserUpdateDto userUpdate, @PathVariable int id) {
        User user = userMapper.fromUpdate(userUpdate);
        return userMapper.toResponse(userService.update(user, id));
    }

    @GetMapping
    public List<UserResponseDto> findAll(@RequestParam(required = false) List<Integer> ids,
                                         @RequestParam(defaultValue = "0") int from,
                                         @RequestParam(defaultValue = "10") int size) {

        int page = from / size;
        return userMapper.toResponse(userService.findAll(ids, page, size));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        userService.deleteById(id);
    }
}
