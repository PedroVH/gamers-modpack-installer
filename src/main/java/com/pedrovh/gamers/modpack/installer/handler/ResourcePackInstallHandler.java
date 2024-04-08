package com.pedrovh.gamers.modpack.installer.handler;

import com.pedrovh.gamers.modpack.installer.gui.InstallerGui;
import com.pedrovh.gamers.modpack.installer.util.InstallerProperties;

import java.nio.file.Paths;

public class ResourcePackInstallHandler extends AbstractInstallHandler {


    public static final String RESOURCEPACKS_DIR = "resourcepacks";
    public static final String FILE_NAME = "resourcepacks.zip";

    public ResourcePackInstallHandler() {
        super(InstallerGui.resourcePacksButton, "Resourcepacks", FILE_NAME, InstallerProperties.get("gamers.resourcepacks.url"));
    }

    @Override
    public void install() {
        String mcDir = getMCDir();
        if (mcDir == null) return;

        if(!Paths.get(mcDir).resolve(RESOURCEPACKS_DIR).toFile().mkdir()) {
            System.out.println("Unable to create resourcepacks directory");
        }
        extractTo(Paths.get(mcDir, RESOURCEPACKS_DIR).toString());
    }
}
