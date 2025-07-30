package com.delprado.springboot.mister.springboot_mister.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.delprado.springboot.mister.springboot_mister.entities.Role;;


public interface RoleRepository extends CrudRepository<Role,Long>{
    Optional<Role> findByName(String name);
}
