package com.pedrovh.gamers.modpack.installer.handler;

import javax.swing.*;
import java.nio.file.Paths;

public class ExtractInstallHandler extends AbstractInstallHandler {

    public ExtractInstallHandler(JButton button, String title, String fileName, String downloadUrl) {
        super(button, title, fileName, downloadUrl);
    }

    @Override
    public void install() {
        String mcDir = getMCDir();
        if (mcDir == null) return;
        extractTo(Paths.get(mcDir, MODS_DIR).toString());
    }

}
