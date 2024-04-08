package com.pedrovh.gamers.modpack.installer.handler;

import com.pedrovh.gamers.modpack.installer.gui.InstallerGui;
import com.pedrovh.gamers.modpack.installer.util.InstallerProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class FabricInstallHandler extends AbstractInstallHandler {

    public static final String FILE_NAME = "fabric_installer.jar";

    public FabricInstallHandler() {
        super(InstallerGui.fabricButton, "Fabric", FILE_NAME, InstallerProperties.get("installer.fabric.url"));
    }

    @Override
    public void install() {
        String installDir = InstallerGui.getInstallLocation();
        if (Objects.isNull(installDir)) {
            pop.error("Selecione um local de instalação válido!");
            return;
        }
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "java", "-Djava.awt.headless=true",
                    "-jar", FILE_NAME,
                    "client",
                    "-dir", installDir,
                    "-mcversion", "1.20.1");
            Process p = pb.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Fabric -> "+line);
            }
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
