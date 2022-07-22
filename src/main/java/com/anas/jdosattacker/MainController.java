package com.anas.jdosattacker;

import com.anas.jdosattacker.args.ArgumentsProcessor;
import com.anas.jdosattacker.request.Requester;
import com.anas.jdosattacker.tui.GetData;

import java.io.IOException;
import java.util.ArrayList;

/**
 * It's a controller class that creates threads and starts them
 */
public class MainController {
    public static final String VERSION = "1.2.0";
    private static int threadsNumber = 0;
    private final ArrayList<Thread> threads;

    /**
     * The constructor.
     * @param args The arguments passed to the program
     */
    public MainController(final String[] args) {
        // It's processing arguments from command line.
        new ArgumentsProcessor().process(args);
        // It's checking if there are important empty fields in the request.
        if (this.hasEmptyFields()) {
            try {
                // If true, start the tui and get the data from the user.
                new GetData();
            } catch (final IOException e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
            }
        }
        this.threads = new ArrayList<>();
        createThreads();
        startThreads();
    }

    /**
     * Start all the threads in the threads array.
     */
    private void startThreads() {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    /**
     * Set the number of threads to use.
     *
     * @param threadsNum number of threads.
     * @throws FieldException if the number of threads is less than 1.
     */
    public static void setThreadsNum(final int threadsNum) throws FieldException {
        if (threadsNum < 1) {
            throw new FieldException("Number of threads must be greater than 0");
        }
        threadsNumber = threadsNum;
    }

    /**
     * Set the threads number from string.
     * @param threadsNumber string number of threads.
     * @throws FieldException if the number of threads is less than 1, or if it's not an integer number.
     */
    public static void setThreadsNum(final String threadsNumber) throws FieldException {
        try {
            final var threadsNum = Integer.parseInt(threadsNumber);
            MainController.setThreadsNum(threadsNum);
        } catch (NumberFormatException e) {
            throw new FieldException("Number of threads must be an integer number");
        }
    }

    /**
     * It creates a new threads and set there names and add them to the threads array.
     */
    public void createThreads() {
        for (var i = 0; i < threadsNumber; i++) {
            threads.add(new Thread(new Requester()));
           threads.get(i).setName("Requester " + (i + 1));
        }
    }

    /**
     * If the requester has empty fields or the number of threads is 0, then return true.
     *
     * @return A boolean value, representing if the requester has empty fields or the number of threads is 0.
     */
    public boolean hasEmptyFields() {
        return Requester.hasRequirementEmptyFields() || threadsNumber == 0;
    }
}
