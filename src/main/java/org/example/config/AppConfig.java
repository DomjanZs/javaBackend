package org.example.config;

import java.io.File;
import java.io.IOException;

public class AppConfig {
    public static void initializeResources() {

        new File(PathConfig.LOGS_PATH).mkdirs();
        new File(PathConfig.DATA_PATH).mkdirs();
        new File(PathConfig.REPORTS_PATH).mkdirs();

        createFile(PathConfig.APPLICATION_LOG_PATH);
    }

    private static void createFile(String filePath) {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
