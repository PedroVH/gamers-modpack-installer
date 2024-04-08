package com.pedrovh.gamers.modpack.installer.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

    public static Path findDefaultInstallDir() {
        Path dir;

        if (OperatingSystem.CURRENT == OperatingSystem.WINDOWS && System.getenv("APPDATA") != null) {
            dir = Paths.get(System.getenv("APPDATA")).resolve(".minecraft");
        } else {
            String home = System.getProperty("user.home", ".");
            Path homeDir = Paths.get(home);

            if (OperatingSystem.CURRENT == OperatingSystem.MACOS) {
                dir = homeDir.resolve("Library").resolve("Application Support").resolve("minecraft");
            } else {
                dir = homeDir.resolve(".minecraft");

                if (OperatingSystem.CURRENT == OperatingSystem.LINUX && !Files.exists(dir)) {
                    // https://github.com/flathub/com.mojang.Minecraft
                    final Path flatpack = homeDir.resolve(".var").resolve("app").resolve("com.mojang.Minecraft").resolve(".minecraft");

                    if (Files.exists(flatpack)) {
                        dir = flatpack;
                    }
                }
            }
        }
        return dir.toAbsolutePath().normalize();
    }

    public static void deleteFile(String name) {
        File myObj = new File(name);
        if (myObj.delete()) {
            System.out.println("Deleted " + myObj.getName());
        } else {
            System.out.println("Failed to delete "+ name);
        }
    }

}
