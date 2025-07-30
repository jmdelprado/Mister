package com.delprado.springboot.mister.springboot_mister.services;

import java.util.List;

import com.delprado.springboot.mister.springboot_mister.entities.User;

public interface UserService {

    List<User> findAll();
    User save(User user);
}
