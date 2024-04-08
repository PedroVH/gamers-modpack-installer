package com.pedrovh.gamers.modpack.installer.util;

import javax.swing.*;

import java.awt.*;

public class UIFactory {

    public static JPanel panel() {
        return new JPanel();
    }

    public static JLabel label(String text) {
        return label(text, JLabel.CENTER);
    }

    public static JLabel label(String text, int horizontalAlignment) {
        return new JLabel(text, horizontalAlignment);
    }

    public static JLabel label(String text, int horizontalAlignment, int size) {
        JLabel label = label(text, horizontalAlignment);
        label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), size));
        return label;
    }

    public static JButton button(String text) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(180, button.getPreferredSize().height));
        return button;
    }

}
