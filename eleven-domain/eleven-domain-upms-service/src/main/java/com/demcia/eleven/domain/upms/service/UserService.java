package com.demcia.eleven.domain.upms.service;

import com.demcia.eleven.domain.upms.entity.User;
import com.demcia.eleven.domain.upms.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void saveUser(User user) {
//        var exist = userRepository.getReferenceById("85");
////        throw new IllegalStateException("假设");
//        exist.setUsername(UUID.randomUUID().toString());
        userRepository.save(user);
    }

    public Optional<User> getUser(Long id) {
//        return userRepository.findById(id);
        return null;
    }
}
