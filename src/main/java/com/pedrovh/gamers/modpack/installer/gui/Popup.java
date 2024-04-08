package com.pedrovh.gamers.modpack.installer.gui;

import com.pedrovh.gamers.modpack.installer.handler.AbstractInstallHandler;
import com.pedrovh.gamers.modpack.installer.util.UIFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Popup extends JFrame implements WindowListener {

    protected final String title;
    protected final AbstractInstallHandler handler;
    protected JLabel labelUpdate;
    private final GridBagConstraints constraint;

    public Popup(String title, AbstractInstallHandler handler) throws HeadlessException {
        this.title = title;
        this.handler = handler;

        JPanel panel = UIFactory.panel();
        panel.setLayout(new GridBagLayout());

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        constraint = new GridBagConstraints();
        constraint.gridwidth = GridBagConstraints.REMAINDER;
        constraint.insets = new Insets(5, 5, 5,5);

        panel.add(progressBar, constraint);

        setContentPane(panel);
    }

    public void start() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String lafCls = UIManager.getSystemLookAndFeelClassName();
        UIManager.setLookAndFeel(lafCls);

        if (lafCls.endsWith("AquaLookAndFeel")) {
            UIManager.put("TabbedPane.foreground", Color.BLACK);
        }

        setTitle(title);
        setLocationRelativeTo(InstallerGui.instance);
        updateSize(true);
        setVisible(true);
        addWindowListener(this);
    }

    public void updateSize(boolean updateMinimum) {
        if (updateMinimum) setMinimumSize(null);
        setPreferredSize(null);
        pack();
        Dimension size = getPreferredSize();
        if (updateMinimum) setMinimumSize(size);
        setPreferredSize(new Dimension(Math.max(250, size.width), size.height));
        setSize(getPreferredSize());
    }

    public void update(String text) {
        if (labelUpdate == null) {
            labelUpdate = new JLabel(text);
            getContentPane().add(labelUpdate, constraint);
        } else {
            labelUpdate.setText(text);
        }
        labelUpdate.setForeground(Color.GRAY);
        updateSize(false);
    }

    public void error(String text) {
        System.out.println("Error: " + text);
        JOptionPane.showMessageDialog(this, text, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void close() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }
    @Override
    public void windowClosing(WindowEvent e) {
        handler.reEnable();
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
