package com.anas.jdosattacker;

import com.anas.jdosattacker.tui.MainTUI;

import java.io.IOException;

public class Main {
    public static MainTUI mainTUI;
    public static void main(String[] args) {
//        new MainController(args); // Just it :D
        try {
           mainTUI = new MainTUI();
           mainTUI.start(args);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
