package com.example.theredditproject.services;

import com.example.theredditproject.models.User;

public interface UserServices {

    void save(User user);
    Iterable<User> getAllUsers();
    User getUserByName(String name);
    User getUserById(Long id);
}
