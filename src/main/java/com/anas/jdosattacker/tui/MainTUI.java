package com.anas.jdosattacker.tui;

import com.anas.jdosattacker.MainController;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;

public class MainTUI extends BasicWindow {
    private Screen screen;
    private MultiWindowTextGUI textGUI;
    private Panel panel;

    public MainTUI() throws IOException {
        initialize();
    }

    public void start(String[] args) throws IOException {
        screen.startScreen();
        MainController mainController = new MainController(args);
        if (mainController.hasEmptyFields()) {
            BasicWindow window = new GetData();
            textGUI.addWindowAndWait(window);
        }
        Label createdThreadsLabel = new Label("Creating threads...");
        panel.addComponent(createdThreadsLabel);
        super.setComponent(panel);
        new Thread(mainController).start();
        textGUI.addListener(new TextGUI.Listener() {
            @Override
            public boolean onUnhandledKeyStroke(TextGUI textGUI, KeyStroke keyStroke) {
                if (keyStroke.getKeyType() == KeyType.Escape) {
                    try {
                        screen.stopScreen();
                        System.exit(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });
        textGUI.addWindowAndWait(this);
    }

    public synchronized void log(String message) {
        panel.addComponent(new Label(message));
    }

    private void initialize() throws IOException {
        screen = new TerminalScreen(new DefaultTerminalFactory().createTerminal());
        screen.doResizeIfNecessary();
        textGUI = new MultiWindowTextGUI(screen, new DefaultWindowManager(),
                new EmptySpace(TextColor.ANSI.DEFAULT));
        panel = new Panel(new GridLayout(1));
    }
}
