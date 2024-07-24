package org.example.services;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerService {
    private static final Logger logger = Logger.getLogger(LoggerService.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("./src/logs/application.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            logger.severe("Failed to initialize logger: " + e.getMessage());
        }
    }

    public static void logSevere(String message) {
        logger.severe(message);
    }
}

