package com.pedrovh.gamers.modpack.installer.handler;

import com.pedrovh.gamers.modpack.installer.gui.InstallerGui;
import com.pedrovh.gamers.modpack.installer.gui.Popup;
import net.lingala.zip4j.ZipFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static java.lang.String.format;

public abstract class AbstractInstallHandler implements ActionListener, Runnable {

    public static final String MODS_DIR = "mods";

    protected final JButton button;
    protected final String title;
    protected final String fileName;
    protected final String downloadUrl;
    protected Popup pop;

    public AbstractInstallHandler(JButton button, String title, String fileName, String downloadUrl) {
        this.button = button;
        this.title = title;
        this.fileName = fileName;
        this.downloadUrl = downloadUrl;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e);
        button.setEnabled(false);
        CompletableFuture.runAsync(this);
    }

    @Override
    public void run() {
        if (!GraphicsEnvironment.isHeadless()) {
            this.pop = new Popup(title, this);
            try {
                pop.start();
            } catch (Exception ex) {
                System.out.println("Error - " + ex);
            }
        }
        if(isNotDownloaded()) {
            System.out.println("Downloading " + title);
            if (!GraphicsEnvironment.isHeadless())
                pop.update("Downloading...");
            download();
        }
        System.out.println("Installing " + title);
        if (!GraphicsEnvironment.isHeadless())
            pop.update("Installing...");
        install();
        if (!GraphicsEnvironment.isHeadless()) {
            pop.update("Done!");
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                System.out.println("Error - " + e.getMessage());
            }
            pop.close();
        }
    }

    public boolean isNotDownloaded() {
        File f = new File(fileName);
        return !f.exists() || f.isDirectory();
    }

    public void download() {
        try {
            download(downloadUrl, fileName);
        } catch (IOException e) {
            if (!GraphicsEnvironment.isHeadless())
                pop.close();
            System.out.println("Error downloading " + title);
        }
    }

    public void download(String urlPath, String fileName) throws IOException {
        URL url = new URL(Objects.requireNonNull(urlPath));
        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        try(FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            FileChannel fileChannel = fileOutputStream.getChannel();
            fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        }
    }

    public abstract void install();

    public void extractTo(String dir) {
        System.out.printf("Extracting to %s%n", dir);

        try(ZipFile zipFile = new ZipFile(fileName)) {
            zipFile.extractAll(dir);
        } catch (IOException e) {
            if (!GraphicsEnvironment.isHeadless()) {
                pop.error(format("Error extracting %s to %s", title, dir));
                pop.close();
            }
        }
    }

    public String getMCDir() {
        String mcDir = InstallerGui.getInstallLocation();
        if (mcDir == null || mcDir.isEmpty() || !new File(mcDir).exists() || !new File(mcDir).isDirectory()) {
            if (!GraphicsEnvironment.isHeadless())
                pop.error("Selecione um local de instalação válido!");
            return null;
        }
        return mcDir;
    }

    public void reEnable() {
        if(button != null)
            button.setEnabled(true);
    }

}
