package com.pedrovh.gamers.modpack.installer.handler;

import com.pedrovh.gamers.modpack.installer.gui.InstallerGui;
import com.pedrovh.gamers.modpack.installer.util.InstallerProperties;

import java.nio.file.Paths;

public class ShaderPackInstallHandler extends AbstractInstallHandler {

    public static final String FILE_NAME = "shaderpacks.zip";
    public static final String SHADERPACKS_DIR = "shaderpacks";

    public ShaderPackInstallHandler() {
        super(InstallerGui.shaderpacksButton, "Shaderpacks", FILE_NAME, InstallerProperties.get("gamers.shaderpacks.url"));
    }

    @Override
    public void install() {
        String mcDir = getMCDir();
        if (mcDir == null) return;

        if(!Paths.get(mcDir).resolve(SHADERPACKS_DIR).toFile().mkdir()) {
            System.out.println("Unable to create shaderpacks directory");
        }
        extractTo(Paths.get(mcDir, SHADERPACKS_DIR).toString());
    }

}
