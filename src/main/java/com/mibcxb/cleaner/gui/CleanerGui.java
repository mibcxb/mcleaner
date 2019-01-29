package com.mibcxb.cleaner.gui;

import javax.swing.*;

public class CleanerGui {
    public static void main(String[] args) {
        MainView mainView = new MainView();
        JFrame frame = new JFrame("清理工具");
        JPanel mainPane = mainView.getMainPanel();
        frame.setContentPane(mainPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(640, 480);
        frame.setResizable(false);
        frame.setLocationRelativeTo(mainPane);//居中
        frame.setVisible(true);
    }
}
