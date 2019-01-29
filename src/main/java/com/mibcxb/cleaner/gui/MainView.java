package com.mibcxb.cleaner.gui;

import com.mibcxb.cleaner.gradle.GradleCleaner;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Position;

public class MainView {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel gradlePanel;
    private JTextField groupIdTextField;
    private JTextField moduleTextField;
    private JTextField versionTextField;
    private JButton buttonCleanGradle;
    private JTextPane resultPane1;

    private DefaultStyledDocument document;

    public MainView() {
        initView();
    }

    private void initView() {
        document = new DefaultStyledDocument();
        resultPane1.setDocument(document);
        resultPane1.setAutoscrolls(true);

        buttonCleanGradle.addActionListener(e -> {
            String groupId = groupIdTextField.getText();
            String module = moduleTextField.getText();
            String version = versionTextField.getText();

            clearDocument();
            GradleCleaner cleaner = new GradleCleaner();
            cleaner.setPrinter(this::showTextLine);
            cleaner.cleanCache(groupId, module, version);
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void clearDocument() {
        try {
            document.remove(0, document.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void showTextLine(String line) {
        insertLine(line);
    }

    private void insertLine(String text) {
        if (text == null) {
            text = "\n";
        } else {
            text = text + "\n";
        }
        insertString(text);
    }

    private void insertString(String text) {
        try {
            Position position = document.getEndPosition();
            if (position == null) {
                document.insertString(0, text, null);
            } else {
                document.insertString(position.getOffset(), text, null);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
