package com.delprado.springboot.mister.springboot_mister.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.delprado.springboot.mister.springboot_mister.entities.Player;

public interface PlayerRepository extends CrudRepository<Player,Long> {

    Optional<Player> findByName(String name);

    @Query("select p from Player p where LOWER(concat(p.name ,' ' , p.lastname)) LIKE LOWER(concat('%',:searchTerm,'%'))")
    List<Player> findContaintFullName(@Param ("searchTerm") String searchTerm);
    
}
