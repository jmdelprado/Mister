package com.delprado.springboot.mister.springboot_mister.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import com.delprado.springboot.mister.springboot_mister.entities.Player;

public interface PlayerService {

    List<Player> findAll();

    Optional<Player> findById(Long id);

    Optional<Player> findByName(String name);

    Player savePlayer(String name, String lastname, Integer numberClau, LocalDate date, MultipartFile imageFile) throws IOException;

    Optional<Player> updated (Long id, Player player);

    Optional<Player> delete (Long id);

    List<Player> findContaintFullName(@Param ("searchTerm") String searchTerm);
}
