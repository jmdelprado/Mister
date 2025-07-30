package com.delprado.springboot.mister.springboot_mister.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.delprado.springboot.mister.springboot_mister.entities.Role;
import com.delprado.springboot.mister.springboot_mister.entities.User;
import com.delprado.springboot.mister.springboot_mister.repositories.RoleRepository;
import com.delprado.springboot.mister.springboot_mister.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        List<User> users = (List<User>) repository.findAll();
        return users;
    }

    @Override
    public User save(User user) {
        Optional<Role> optionalRole = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        if(optionalRole.isPresent()){
            roles.add(optionalRole.get());
            
        } 
        user.setEnabled(true);
        user.setRoles(roles);

        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);

        return repository.save(user);
    }

}
