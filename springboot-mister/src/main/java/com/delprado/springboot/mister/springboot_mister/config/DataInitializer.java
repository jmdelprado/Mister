package com.delprado.springboot.mister.springboot_mister.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.delprado.springboot.mister.springboot_mister.repositories.PlayerRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public void run(String... args) throws Exception {
        logger.info("=== INICIALIZACIÓN DE LA APLICACIÓN ===");
        logger.info("Verificando conexión a la base de datos...");

        try {
            // Intentar contar los players para verificar que la tabla existe
            long count = playerRepository.count();
            logger.info("✅ Tabla 'players' creada correctamente. Número de registros: {}", count);
        } catch (Exception e) {
            logger.error("❌ Error al verificar la tabla 'players': {}", e.getMessage());
            logger.error("Detalles del error:", e);
        }

        logger.info("=== FIN DE INICIALIZACIÓN ===");
    }
}