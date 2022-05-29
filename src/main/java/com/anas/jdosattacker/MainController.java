package com.anas.jdosattacker;

import com.anas.jdosattacker.args.ArgumentsProcessor;
import com.anas.jdosattacker.request.Requester;
import com.anas.jdosattacker.tui.GetData;

import java.io.IOException;

public class MainController implements Runnable {
    public static final String VERSION = "1.0.0";
    private static int THREADS = 100;

    public MainController(String[] args) {
        new ArgumentsProcessor().process(args);
        if (this.hasEmptyFields()) {
            try {
                new GetData();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        createThreads();
    }

    public static void setThreadsNum(int threads) {
        THREADS = threads;
    }

    public void createThreads() {
        for (int i = 0; i < THREADS; i++) {
           Thread thread = new Thread(new Requester());
           thread.setName("Requester " + (i + 1));
           thread.start();
        }
    }

    public boolean hasEmptyFields() {
        return Requester.url.isBlank() ||
                Requester.USER_AGENT.isBlank() ||
                Requester.REQUEST_METHOD.isBlank() ||
                Requester.CONNECT_TIMEOUT == 0 ||
                Requester.getReqNumber() == 0 ||
                THREADS == 0;
    }

    @Override
    public void run() {
        createThreads();
    }
}
