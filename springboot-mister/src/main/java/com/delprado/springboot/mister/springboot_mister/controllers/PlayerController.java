package com.delprado.springboot.mister.springboot_mister.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.delprado.springboot.mister.springboot_mister.entities.Player;
import com.delprado.springboot.mister.springboot_mister.services.PlayerService;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" })
@RequestMapping("/mister/players")
public class PlayerController {

    @Autowired
    private PlayerService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Player>> findAll() {
        try {
            List<Player> players = service.findAll();
            return ResponseEntity.ok(players);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search/{searchTerm}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> search(@PathVariable String searchTerm) {
        try {
            List<Player> players = service.findContaintFullName(searchTerm);
            if (players.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(players);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(
            @RequestParam("name") String name,
            @RequestParam("lastname") String lastname,
            @RequestParam("numberClau") Integer numberClau,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("image") MultipartFile imageFile) {
        try {
            // Validaciones
            if (name == null || name.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El nombre es requerido");
            }

            if (lastname == null || lastname.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El apellido es requerido");
            }

            if (numberClau == null || numberClau <= 0) {
                return ResponseEntity.badRequest().body("El número de cláusula debe ser mayor a 0");
            }

            if (date == null) {
                return ResponseEntity.badRequest().body("La fecha es requerida");
            }

            if (imageFile == null || imageFile.isEmpty()) {
                return ResponseEntity.badRequest().body("La imagen es requerida");
            }

            // Validar tipo de archivo
            String contentType = imageFile.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest().body("El archivo debe ser una imagen");
            }

            Player saved = service.savePlayer(name.trim(), lastname.trim(), numberClau, date, imageFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la imagen: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Player player, @PathVariable Long id) {
        try {
            Optional<Player> optionalPlayer = service.findById(id);
            if (optionalPlayer.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(service.updated(id, player));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Optional<Player> optionalPlayer = service.findById(id);
            if (optionalPlayer.isPresent()) {
                service.delete(id);
                return ResponseEntity.ok(optionalPlayer.orElseThrow());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
