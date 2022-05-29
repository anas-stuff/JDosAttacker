package com.anas.jdosattacker;

import com.anas.jdosattacker.args.ArgumentsProcessor;
import com.anas.jdosattacker.request.Requester;
import com.anas.jdosattacker.tui.GetData;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {
    public static final String version = "1.0.0";
    private static int threadsNumber = 0;
    private final ArrayList<Thread> threads;

    public MainController(String[] args) {
        new ArgumentsProcessor().process(args);
        if (this.hasEmptyFields()) {
            try {
                new GetData();
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
            }
        }
        this.threads = new ArrayList<>();
        createThreads();
        startThreads();
    }

    private void startThreads() {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public static void setThreadsNum(int threads) {
        threadsNumber = threads;
    }

    public void createThreads() {
        for (int i = 0; i < threadsNumber; i++) {
            threads.add(new Thread(new Requester()));
           threads.get(i).setName("Requester " + (i + 1));
        }
    }

    public boolean hasEmptyFields() {
        return Requester.url.isBlank() ||
                Requester.USER_AGENT.isBlank() ||
                Requester.REQUEST_METHOD.isBlank() ||
                Requester.CONNECT_TIMEOUT == 0 ||
                Requester.getReqNumber() == 0 ||
                threadsNumber == 0;
    }
}
