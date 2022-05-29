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

    public static void setThreadsNum(int threadsNum) throws FieldException {
        if (threadsNum < 1) {
            throw new FieldException("Number of threads must be greater than 0");
        }
        threadsNumber = threadsNum;
    }

    public static void setThreadsNum(String threadsNumber) throws FieldException {
        try {
            int threadsNum = Integer.parseInt(threadsNumber);
            MainController.setThreadsNum(threadsNum);
        } catch (NumberFormatException e) {
            throw new FieldException("Number of threads must be a number");
        }
    }

    public void createThreads() {
        for (int i = 0; i < threadsNumber; i++) {
            threads.add(new Thread(new Requester()));
           threads.get(i).setName("Requester " + (i + 1));
        }
    }

    public boolean hasEmptyFields() {
        return Requester.hasRequmentEmptyFields() || threadsNumber == 0;
    }
}
