package com.anas.jdosattacker.tui;

import com.anas.jdosattacker.FieldException;
import com.anas.jdosattacker.Main;
import com.anas.jdosattacker.request.Requester;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;

/**
 * It creates a window that asks the user for the data needed to start the attack
 */
public class GetData extends BasicWindow {
    private Panel panel;
    private TextBox urlTextBox,
            userAgentTextBox,
            threadsTextBox,
            connectionTimeoutTextBox,
            numberOfRequestsTextBox;
    private ComboBox<String> requestMethodComboBox;
    private Button button;
    private Screen screen;
    private MultiWindowTextGUI textGUI;

    /**
     * The constructor.
     * @throws IOException if there is an error in creating the window.
     */
    public GetData() throws IOException {
        init();
        addButtonListener();
        addComponentsToPanel();
        setupCombobox();
        urlTextBox.setPreferredSize(new TerminalSize(60, 1));
        // add the panel to the window
        super.setComponent(panel);
        screen.doResizeIfNecessary();
        screen.startScreen();
        textGUI.addWindowAndWait(this);
        super.close();
        screen.stopScreen();
    }

    /**
     * This function adds two items to the requestMethodComboBox.
     */
    private void setupCombobox() {
        requestMethodComboBox.addItem("GET");
        requestMethodComboBox.addItem("POST");
    }

    /**
     * This function adds the listener to the button.
     *
     * The listener gets the data from the text boxes and try to set the data to the requester.
     * If there is an error in the data, it shows a message dialog.
     */
    private void addButtonListener() {
        button.addListener(b -> {
            try {
                Requester.setReqNumber(numberOfRequestsTextBox.getText());
                Main.setThreadsNum(threadsTextBox.getText());
                Requester.setConnectTimeout(connectionTimeoutTextBox.getText());
                Requester.setUrl(urlTextBox.getText());
                Requester.setUserAgent(userAgentTextBox.getText());
                Requester.setRequestMethod(requestMethodComboBox.getSelectedItem());
                // close window and start attack
                super.close();
            } catch (final FieldException e) {
                MessageDialog.showMessageDialog(getTextGUI(), "Error", e.getMessage());
            }
        });
    }

    /**
     * It adds all the components to the panel
     */
    private void addComponentsToPanel() {
        panel.addComponent(new Label("URL: "));
        panel.addComponent(urlTextBox);
        panel.addComponent(new Label("User-Agent: "));
        panel.addComponent(userAgentTextBox);
        panel.addComponent(new Label("Request Method: "));
        panel.addComponent(requestMethodComboBox);
        panel.addComponent(new Label("Threads: "));
        panel.addComponent(threadsTextBox);
        panel.addComponent(new Label("Connection Timeout: "));
        panel.addComponent(connectionTimeoutTextBox);
        panel.addComponent(new Label("Number of Requests: "));
        panel.addComponent(numberOfRequestsTextBox);
        panel.addComponent(new EmptySpace(new TerminalSize(0, 0))); // empty space
        panel.addComponent(button);
    }

    /**
     * This function initializes the window.
     * @throws IOException if there is an error in creating the screen.
     */
    private void init() throws IOException {
        screen = new DefaultTerminalFactory().createScreen(); // create screen
        textGUI = new MultiWindowTextGUI(screen, new DefaultWindowManager(),
                new EmptySpace(TextColor.ANSI.BLUE)); // create textGUI

        // create the panel and set the layout
        panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        // Initialize the text boxes with the default values or the arguments passed to the program.
        urlTextBox = new TextBox(Requester.getUrl());
        userAgentTextBox = new TextBox(Requester.getUserAgent());
        threadsTextBox = new TextBox();
        connectionTimeoutTextBox = new TextBox(Requester.getConnectTimeout() + "");
        numberOfRequestsTextBox = new TextBox(Requester.getReqNumber() + "");
        // Initialize the combobox and the start button.
        requestMethodComboBox = new ComboBox<>();
        button = new Button("Start");
    }
}
