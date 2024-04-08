package com.pedrovh.gamers.modpack.installer;

import com.pedrovh.gamers.modpack.installer.gui.InstallerGui;
import com.pedrovh.gamers.modpack.installer.handler.ServerModpackInstallHandler;
import com.pedrovh.gamers.modpack.installer.util.Utils;

import java.awt.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Loading Gamers Modpack Installer");

        if (GraphicsEnvironment.isHeadless()) {
            if (Arrays.stream(args).anyMatch(a->a.contains("server"))) {
                new ServerModpackInstallHandler().run();
                Utils.deleteFile(ServerModpackInstallHandler.FILE_NAME);
            }
        } else {
            InstallerGui.start();
        }
    }

}