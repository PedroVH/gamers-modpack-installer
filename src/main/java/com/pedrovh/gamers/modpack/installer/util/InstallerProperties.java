package com.pedrovh.gamers.modpack.installer.util;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class InstallerProperties {

    private static final Properties prop = new Properties();

    public static String get(String key, String defaultValue) {
        return Optional.ofNullable(get(key)).orElse(defaultValue);
    }

    public static String get(String key) {
        if (prop.isEmpty()) {
            load();
        }
        String value = prop.getProperty(key);
        if (value == null) {
            System.out.printf("Value for key '%s' not found in properties%n", key);
            return null;
        }

        if (value.startsWith("${") && value.endsWith("}"))
            value = System.getenv(value.substring(2, value.length() - 1));

        return value;
    }

    public static void load() {
        try {
            prop.load(InstallerProperties.class.getClassLoader().getResourceAsStream("application.properties"));
        }
        catch (IOException ex) {
            System.out.println("Error reading application.properties");
        }
    }

}
