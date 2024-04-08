package com.pedrovh.gamers.modpack.installer.gui;

import com.pedrovh.gamers.modpack.installer.handler.*;
import com.pedrovh.gamers.modpack.installer.util.UIFactory;
import com.pedrovh.gamers.modpack.installer.util.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class InstallerGui extends JFrame implements WindowListener {

    public static InstallerGui instance;
    public static JButton installButton;
    public static JButton showAdvancedButton;
    public static JButton fabricButton;
    public static JButton serverModpackButton;
    public static JButton clientModpackButton;
    public static JButton irisButton;
    public static JButton shaderpacksButton;
    public static JButton resourcePacksButton;

    private static JTextField mcDirField;

    public InstallerGui() throws HeadlessException {
        instance = this;
        initComponents();
    }

    private void initComponents() {
        JPanel panel = UIFactory.panel();

        JPanel panelTitle = UIFactory.panel();

        Image gif = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemClassLoader().getResource("minecraft.gif"));
        Image image = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemClassLoader().getResource("icon.png"));

        JLabel labelTitle = UIFactory.label("amers Modpack", JLabel.CENTER, 32);
        labelTitle.setIcon(new ImageIcon(image));

        JLabel labelMeme = new JLabel(new ImageIcon(gif), JLabel.CENTER);

        JPanel panelMcDir = UIFactory.panel();

        JLabel labelSelectDir = UIFactory.label("Pasta do Minecraft");

        mcDirField = new JTextField(20);
        mcDirField.setText(Utils.findDefaultInstallDir().toString());

        JButton selectFolderButton = UIFactory.button("...");
        //noinspection SuspiciousNameCombination
        selectFolderButton.setPreferredSize(new Dimension(mcDirField.getPreferredSize().height, mcDirField.getPreferredSize().height));
        selectFolderButton.addActionListener(e -> InstallerGui.selectInstallLocation(mcDirField::getText, mcDirField::setText));

        installButton = UIFactory.button("Instalar");
        installButton.setToolTipText("Instala tudo!");
        installButton.addActionListener(new InstallHandler());

        JPanel panelAdvanced = UIFactory.panel();

        showAdvancedButton = UIFactory.button("Opções Avançadas ⬇");
        showAdvancedButton.addActionListener(new ActionListener() {
            private boolean showing;

            @Override
            public void actionPerformed(ActionEvent e) {
                panelAdvanced.setVisible(showing = !showing);
                updateSize(true);
            }
        });

        JPanel panelRequired = UIFactory.panel();

        JLabel labelRequired = UIFactory.label("Instalações Obrigatórias", JLabel.CENTER, 22);

        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(400,1));

        fabricButton = UIFactory.button("Fabric");
        fabricButton.setToolTipText("Através do instalador oficial");
        fabricButton.addActionListener(new FabricInstallHandler());

        serverModpackButton = UIFactory.button("Mods do Server");
        serverModpackButton.setToolTipText("Através deste instalador");
        serverModpackButton.addActionListener(new ServerModpackInstallHandler());

        JPanel panelOptional = UIFactory.panel();

        JLabel labelOptional = UIFactory.label("Instalações Opcionais", JLabel.CENTER, 22);

        JSeparator separator2 = new JSeparator();
        separator2.setPreferredSize(new Dimension(400,1));

        clientModpackButton = UIFactory.button("Mods de Cliente");
        clientModpackButton.setToolTipText("Através deste instalador");
        clientModpackButton.addActionListener(new ClientModpackInstallHandler());

        irisButton = UIFactory.button("Iris");
        irisButton.setToolTipText("Através deste instalador");
        irisButton.addActionListener(new IrisModpackInstallHandler());

        shaderpacksButton = UIFactory.button("Shaderpacks");
        shaderpacksButton.setToolTipText("Através deste instalador");
        shaderpacksButton.addActionListener(new ShaderPackInstallHandler());

        resourcePacksButton = UIFactory.button("Resourcepacks");
        resourcePacksButton.setToolTipText("Através deste instalador");
        resourcePacksButton.addActionListener(new ResourcePackInstallHandler());

        JLabel labelAuthor = UIFactory.label("Feito por Pepe Gamer", JLabel.RIGHT);

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 5, 5,5);

        GridBagConstraints cTitle = new GridBagConstraints();
        cTitle.gridwidth = GridBagConstraints.REMAINDER;
        cTitle.insets = new Insets(0, 0, 25, 0);

        GridBagConstraints cFooter = new GridBagConstraints();
        cFooter.gridwidth = GridBagConstraints.REMAINDER;
        cFooter.fill = GridBagConstraints.HORIZONTAL;
        cFooter.insets = new Insets(25, 0, 0, 0);

        panelTitle.setLayout(new GridBagLayout());
        panelTitle.add(labelMeme, c);
        panelTitle.add(labelTitle);

        panelMcDir.setLayout(new GridBagLayout());
        panelMcDir.add(labelSelectDir, c);
        panelMcDir.add(mcDirField);
        panelMcDir.add(selectFolderButton, c);

        panelRequired.setLayout(new GridBagLayout());
        panelRequired.add(labelRequired, c);
        panelRequired.add(separator, c);
        panelRequired.add(fabricButton, c);
        panelRequired.add(serverModpackButton, c);

        panelOptional.setLayout(new GridBagLayout());
        panelOptional.add(labelOptional, c);
        panelOptional.add(separator2, c);
        panelOptional.add(clientModpackButton, c);
        panelOptional.add(irisButton, c);
        panelOptional.add(shaderpacksButton, c);
        panelOptional.add(resourcePacksButton, c);

        panelAdvanced.setLayout(new GridBagLayout());
        panelAdvanced.add(panelRequired, c);
        panelAdvanced.add(panelOptional, c);
        panelAdvanced.setVisible(false);

        //noinspection DuplicatedCode
        panel.setLayout(new GridBagLayout());
        panel.add(panelTitle, cTitle);
        panel.add(panelMcDir, c);
        panel.add(installButton, c);
        panel.add(showAdvancedButton, c);
        panel.add(panelAdvanced, c);
        panel.add(labelAuthor, cFooter);

        setContentPane(panel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(image);

        this.addWindowListener(this);
    }

    public static void start() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        String lafCls = UIManager.getSystemLookAndFeelClassName();
        UIManager.setLookAndFeel(lafCls);

        if (lafCls.endsWith("AquaLookAndFeel")) {
            UIManager.put("TabbedPane.foreground", Color.BLACK);
        }

        InstallerGui dialog = new InstallerGui();
        dialog.updateSize(true);
        dialog.setTitle("Gamers Modpack Installer (real!!!!)");
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public void updateSize(boolean updateMinimum) {
        if (updateMinimum) setMinimumSize(null);
        setPreferredSize(null);
        pack();
        Dimension size = getPreferredSize();
        if (updateMinimum) setMinimumSize(size);
        setPreferredSize(new Dimension(Math.max(400, size.width), size.height));
        setSize(getPreferredSize());
    }

    public static void selectInstallLocation(Supplier<String> initalDir, Consumer<String> selectedDir) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(initalDir.get()));
        chooser.setDialogTitle("Selecione a pasta");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            selectedDir.accept(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    public static String getInstallLocation() {
        return mcDirField.getText();
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }
    @Override
    public void windowClosing(WindowEvent e) {
        Utils.deleteFile(FabricInstallHandler.FILE_NAME);
        Utils.deleteFile(ServerModpackInstallHandler.FILE_NAME);
        Utils.deleteFile(ClientModpackInstallHandler.FILE_NAME);
        Utils.deleteFile(IrisModpackInstallHandler.FILE_NAME);
        Utils.deleteFile(ShaderPackInstallHandler.FILE_NAME);
        Utils.deleteFile(ResourcePackInstallHandler.FILE_NAME);
    }
    @Override
    public void windowClosed(WindowEvent e) {
    }
    @Override
    public void windowIconified(WindowEvent e) {
    }
    @Override
    public void windowDeiconified(WindowEvent e) {
    }
    @Override
    public void windowActivated(WindowEvent e) {
    }
    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
