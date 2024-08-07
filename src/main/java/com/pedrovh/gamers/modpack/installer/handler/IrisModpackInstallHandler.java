package com.pedrovh.gamers.modpack.installer.handler;

import com.pedrovh.gamers.modpack.installer.gui.InstallerGui;
import com.pedrovh.gamers.modpack.installer.util.InstallerProperties;
import net.lingala.zip4j.ZipFile;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

public class IrisModpackInstallHandler extends AbstractInstallHandler {

    public static final String FILE_NAME = "iris-sodium.zip";

    public IrisModpackInstallHandler() {
        super(InstallerGui.irisButton, "Iris", FILE_NAME, InstallerProperties.get("modpack.iris.url"));
    }

    @Override
    public void install() {
        String mcDir = getMCDir();
        if (mcDir == null) return;
        File modsDir = Paths.get(mcDir, MODS_DIR).toFile();

        System.out.println("Checking for optifine");

        for (File mod : Objects.requireNonNull(modsDir.listFiles())) {
            if (mod.getName().toLowerCase().contains("optifine") || mod.getName().toLowerCase().contains("optifabric")) {
                int result = JOptionPane.showOptionDialog(InstallerGui.instance, "Você tem o optifine instalado, mas é incompatível com Iris. Quer remover ou cancelar a instalação?",
                        "Optifine Detectado",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, new String[]{"Sim", "Cancelar"}, "Sim");

                if (result != JOptionPane.YES_OPTION) {
                    pop.close();
                    return;
                }
                if (!mod.delete()) {
                    pop.error("Falha ao remover Optifine!");
                    return;
                }
            }
        }
        for (File mod : Objects.requireNonNull(modsDir.listFiles())) {
            if (mod.getName().toLowerCase().contains("iris") || mod.getName().toLowerCase().contains("sodium-fabric")) {
                if (!mod.delete()) {
                    pop.error("Falha ao remover Optifine");
                    return;
                }
            }
        }
        if(!Paths.get(mcDir).resolve("shaderpacks").toFile().mkdir()) {
            System.out.println("Unable to create shaderpacks directory");
        }

        System.out.printf("Extracting to %s%n", modsDir);

        try(ZipFile zipFile = new ZipFile(FILE_NAME)) {
            zipFile.extractAll(mcDir);
        } catch (IOException e) {
            pop.error("Error extracting Iris");
            pop.close();
        }
    }

}
