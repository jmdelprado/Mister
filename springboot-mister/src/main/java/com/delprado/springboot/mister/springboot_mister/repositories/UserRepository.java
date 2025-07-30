package com.delprado.springboot.mister.springboot_mister.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.delprado.springboot.mister.springboot_mister.entities.User;

public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByUsername(String username);

}
