package com.delprado.springboot.mister.springboot_mister.services;

import org.springframework.stereotype.Service;

import com.delprado.springboot.mister.springboot_mister.entities.Role;

@Service
public class RoleServiceImpl implements RoleService {

    @Override
    public Role findByName(String name) {
        return findByName(name);
    }

}
