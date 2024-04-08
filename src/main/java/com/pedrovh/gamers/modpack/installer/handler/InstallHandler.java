package com.pedrovh.gamers.modpack.installer.handler;

import com.pedrovh.gamers.modpack.installer.gui.InstallerGui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

public class InstallHandler implements ActionListener, Runnable {

    private final FabricInstallHandler fabric;
    private final ServerModpackInstallHandler server;
    private final ClientModpackInstallHandler client;
    private final IrisModpackInstallHandler iris;
    private final ShaderPackInstallHandler shaders;
    private final ResourcePackInstallHandler resource;

    public InstallHandler() {
        this.fabric = new FabricInstallHandler();
        this.server = new ServerModpackInstallHandler();
        this.client = new ClientModpackInstallHandler();
        this.iris = new IrisModpackInstallHandler();
        this.shaders = new ShaderPackInstallHandler();
        this.resource = new ResourcePackInstallHandler();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setAllEnabled(false);
        CompletableFuture.runAsync(this)
                .thenRun(() -> setAllEnabled(true));
    }

    @Override
    public void run() {
        fabric.run();
        server.run();
        client.run();
        iris.run();
        shaders.run();
        resource.run();
    }

    public void setAllEnabled(boolean enabled) {
        InstallerGui.installButton.setEnabled(enabled);
        InstallerGui.showAdvancedButton.setEnabled(enabled);
        InstallerGui.fabricButton.setEnabled(enabled);
        InstallerGui.serverModpackButton.setEnabled(enabled);
        InstallerGui.clientModpackButton.setEnabled(enabled);
        InstallerGui.irisButton.setEnabled(enabled);
        InstallerGui.shaderpacksButton.setEnabled(enabled);
        InstallerGui.resourcePacksButton.setEnabled(enabled);
    }

}
