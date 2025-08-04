package com.delprado.springboot.mister.springboot_mister.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.delprado.springboot.mister.springboot_mister.entities.User;
import com.delprado.springboot.mister.springboot_mister.services.UserService;

@RestController
@CrossOrigin(origins = { "http://localhost:4200", "https://mister-ei6q.onrender.com" })
@RequestMapping("/mister/users")
public class UserController {

  @Autowired
  private UserService service;

  @GetMapping
  private List<User> list() {

    return service.findAll();

  }

  @PostMapping("/register")
  private ResponseEntity<?> create(@RequestBody User user) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
  }

}
