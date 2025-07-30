package com.delprado.springboot.mister.springboot_mister.services;

import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.delprado.springboot.mister.springboot_mister.entities.Player;
import com.delprado.springboot.mister.springboot_mister.repositories.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository repository;

    @Override
    public List<Player> findAll() {
        return (List<Player>) repository.findAll();
    }

    @Override
    public Optional<Player> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Player> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Player savePlayer(String name, String lastname, Integer numberClau, LocalDate date, MultipartFile imageFile)
            throws IOException {
        try {
            // Validar que el archivo no esté vacío
            if (imageFile == null || imageFile.isEmpty()) {
                throw new IllegalArgumentException("La imagen es requerida");
            }

            // Validar el tamaño del archivo (máximo 2MB para evitar problemas con la base
            // de datos)
            if (imageFile.getSize() > 2 * 1024 * 1024) {
                throw new IllegalArgumentException("La imagen no puede ser mayor a 2MB");
            }

            // Validar el tipo de archivo
            String contentType = imageFile.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IllegalArgumentException("El archivo debe ser una imagen");
            }

            // Convertir la imagen a Base64
            String imageBase64 = Base64.getEncoder().encodeToString(imageFile.getBytes());

            // Crear el jugador
            Player player = new Player();
            player.setName(name);
            player.setLastname(lastname);
            player.setNumberClau(numberClau);
            player.setDate(java.sql.Date.valueOf(date));
            player.setImage(imageBase64);

            // Guardar en la base de datos
            Player savedPlayer = repository.save(player);

            // Limpiar la imagen del objeto devuelto para evitar respuestas muy grandes
            savedPlayer.setImage(null);

            return savedPlayer;

        } catch (IOException e) {
            throw new IOException("Error al procesar la imagen: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el jugador: " + e.getMessage());
        }
    }

    @Override
    public Optional<Player> updated(Long id, Player player) {
        try {
            Optional<Player> playerOptional = repository.findById(id);
            if (playerOptional.isPresent()) {
                Player playerUpdate = playerOptional.orElseThrow();
                playerUpdate.setName(player.getName());
                playerUpdate.setLastname(player.getLastname());
                playerUpdate.setImage(player.getImage());
                playerUpdate.setNumberClau(player.getNumberClau());
                playerUpdate.setDate(new Date());
                return Optional.of(repository.save(playerUpdate));
            }
            return playerOptional;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el jugador: " + e.getMessage());
        }
    }

    @Override
    public Optional<Player> delete(Long id) {
        try {
            Optional<Player> playerOptional = repository.findById(id);
            if (playerOptional.isPresent()) {
                Player playerDB = playerOptional.orElseThrow();
                repository.delete(playerDB);
            }
            return playerOptional;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el jugador: " + e.getMessage());
        }
    }

    @Override
    public List<Player> findContaintFullName(String searchTerm) {
        try {
            List<Player> players = repository.findContaintFullName(searchTerm);
            return players;
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar jugadores: " + e.getMessage());
        }
    }
}
