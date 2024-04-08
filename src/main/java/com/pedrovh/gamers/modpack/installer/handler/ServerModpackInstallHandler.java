package com.pedrovh.gamers.modpack.installer.handler;

import com.pedrovh.gamers.modpack.installer.gui.InstallerGui;
import com.pedrovh.gamers.modpack.installer.util.InstallerProperties;

public class ServerModpackInstallHandler extends ExtractInstallHandler {

    public static final String FILE_NAME = "server_modpack.zip";

    public ServerModpackInstallHandler() {
        super(InstallerGui.serverModpackButton, "Server Mods", FILE_NAME, InstallerProperties.get("gamers.modpack.server.url"));
    }

}
