package com.example.theredditproject.services;

import com.example.theredditproject.exceptions.UserException;
import com.example.theredditproject.models.User;
import com.example.theredditproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceCRUD implements UserServices {

    private UserRepository userRepository;

    @Autowired
    public UserServiceCRUD(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByName(String name) {
        User user = userRepository.findByName(name);
        if (user == null) {
            throw new UserException("No user found for name: " + name);
        }
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
