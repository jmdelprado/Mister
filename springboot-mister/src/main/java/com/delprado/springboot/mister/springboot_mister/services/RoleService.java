package com.delprado.springboot.mister.springboot_mister.services;

import com.delprado.springboot.mister.springboot_mister.entities.Role;

public interface RoleService {
    Role findByName(String name);
}
