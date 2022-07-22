package com.anas.jdosattacker;

import com.anas.jdosattacker.args.ArgumentsProcessor;
import com.anas.jdosattacker.request.Requester;
import com.anas.jdosattacker.tui.GetData;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The main class.
 */
public class Main {
    public static final String VERSION;
    private static int threadsNumber;
    private static final ArrayList<Thread> threads;

    static {
        VERSION = "1.3.0";
        threadsNumber = 0;
        threads = new ArrayList<>();
    }

    /**
     * The entry point of the program.
     *
     * @param args The arguments passed to the program
     */
    public static void main(final String[] args) {
        // It's processing arguments from command line.
        new ArgumentsProcessor().process(args);
        // It's checking if there are important empty fields in the request.
        if (Main.hasEmptyFields()) {
            try {
                // If true, start the tui and get the data from the user.
                new GetData();
            } catch (final IOException e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
            }
        }
        createThreads();
        startThreads();
    }

    /**
     * Start all the threads in the threads array.
     */
    private static void startThreads() {
        for (final var thread : threads) {
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
     *
     * @param threadsNumber string number of threads.
     * @throws FieldException if the number of threads is less than 1, or if it's not an integer number.
     */
    public static void setThreadsNum(final String threadsNumber) throws FieldException {
        try {
            Main.setThreadsNum(Integer.parseInt(threadsNumber));
        } catch (final NumberFormatException e) {
            throw new FieldException("Number of threads must be an integer number");
        }
    }

    /**
     * It creates a new threads and set there names and add them to the threads array.
     */
    public static void createThreads() {
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
    public static boolean hasEmptyFields() {
        return Requester.hasRequirementEmptyFields() || threadsNumber == 0;
    }
}
