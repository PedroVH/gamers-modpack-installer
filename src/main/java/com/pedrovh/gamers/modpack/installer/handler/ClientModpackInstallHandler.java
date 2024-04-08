package com.pedrovh.gamers.modpack.installer.handler;

import com.pedrovh.gamers.modpack.installer.gui.InstallerGui;
import com.pedrovh.gamers.modpack.installer.util.InstallerProperties;

public class ClientModpackInstallHandler extends ExtractInstallHandler {

    public static final String FILE_NAME = "client_modpack.zip";

    public ClientModpackInstallHandler() {
        super(InstallerGui.clientModpackButton, "Client Mods", FILE_NAME, InstallerProperties.get("gamers.modpack.client.url"));
    }

}
