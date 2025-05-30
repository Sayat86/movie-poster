package com.example.eventsdiscovery.user;

import com.example.eventsdiscovery.exception.ConflictException;
import com.example.eventsdiscovery.exception.NotFoundException;
import com.example.eventsdiscovery.user.dto.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User create(User user) {
        Optional<User> existingEmail = userRepository.findByEmail(user.getEmail());
        if (existingEmail.isPresent()) {
            throw new ConflictException("Пользователь с такой почтой уже существует");
        }
        return userRepository.save(user);
    }

    @Override
    public User update(User updateUser, int id) {
        Optional<User> optionalEmail = userRepository.findByEmail(updateUser.getEmail());

        if (optionalEmail.isPresent()) {
            User foundUserByEmail = optionalEmail.get();
            if (foundUserByEmail.getId() != id) {
                throw new ConflictException("Пользователь с такой почтой уже существует");
            }
        }
        User existingUser = findById(id);
        userMapper.merge(existingUser, updateUser);
        return userRepository.save(existingUser);
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с таким ID не найден"));
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll(List<Integer> ids, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (ids == null || ids.isEmpty()) {
            return userRepository.findAll(pageable).getContent();
        }

        Page<User> pageResult = userRepository.findByIdIn(ids, pageable);
        return pageResult.getContent();
    }
}
