package com.anas.jdosattacker.tui;

import com.anas.jdosattacker.MainController;
import com.anas.jdosattacker.request.Requester;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;

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

    public GetData() throws IOException {
        init();
        addButtonListener();
        addComponentsToPanel();
        setupCombobox();
        setupComponentsPreferredSize();
        // add panel to window
        super.setComponent(panel);
        screen.doResizeIfNecessary();
        screen.startScreen();
        textGUI.addWindowAndWait(this);
        super.close();
        screen.stopScreen();
    }

    private void setupComponentsPreferredSize() {
        urlTextBox.setPreferredSize(new TerminalSize(60, 1));
    }

    private void setupCombobox() {
        requestMethodComboBox.addItem("GET");
        requestMethodComboBox.addItem("POST");
    }

    private void addButtonListener() {
        button.addListener((button) -> {
            // Check if all fields are filled
            if (urlTextBox.getText().isBlank() ||
                    userAgentTextBox.getText().isBlank() ||
                    threadsTextBox.getText().isBlank() ||
                    connectionTimeoutTextBox.getText().isBlank() ||
                    numberOfRequestsTextBox.getText().isBlank()) {
                // If not, show error message
                MessageDialog.showMessageDialog(getTextGUI(), "Error", "Please fill all fields");
            } else {
                // If yes, close window and start attack
                close();
                Requester.url = urlTextBox.getText();
                Requester.setReqNumber(Integer.parseInt(numberOfRequestsTextBox.getText()));
                Requester.USER_AGENT = userAgentTextBox.getText();
                Requester.REQUEST_METHOD = requestMethodComboBox.getSelectedItem();
                Requester.CONNECT_TIMEOUT = Integer.parseInt(connectionTimeoutTextBox.getText());
                MainController.setThreadsNum(Integer.parseInt(threadsTextBox.getText()));
            }
        });
    }

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

    private void init() throws IOException {
        panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        urlTextBox = new TextBox(Requester.url);
        userAgentTextBox = new TextBox(Requester.USER_AGENT);
        threadsTextBox = new TextBox();
        connectionTimeoutTextBox = new TextBox(Requester.CONNECT_TIMEOUT + "");
        numberOfRequestsTextBox = new TextBox(Requester.getReqNumber() + "");
        requestMethodComboBox = new ComboBox<>();
        button = new Button("Start");

        screen = new DefaultTerminalFactory().createScreen();
        textGUI = new MultiWindowTextGUI(screen, new DefaultWindowManager(),
                new EmptySpace(TextColor.ANSI.BLUE));
    }
}
