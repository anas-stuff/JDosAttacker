package com.anas.jdosattacker.args;

import com.anas.jdosattacker.FieldException;
import com.anas.jdosattacker.MainController;
import com.anas.jdosattacker.request.Requester;
import org.apache.commons.cli.*;

/**
 * It parses the command line arguments and sets the corresponding variables, and if there are any errors, it prints the
 * help and exits
 */
public class ArgumentsProcessor {
    private final Options options;

    /**
     * The constructor.
     */
    public ArgumentsProcessor() {
        options = new Options();
        setupOptions();
    }

    /**
     * Set up the command line options.
     */
    private void setupOptions() {
        options.addOption("h", "help", false, "Print this help");
        options.addOption("v", "version", false, "Print version");
        options.addOption("u", "url", true, "URL to attack");
        options.addOption("t", "threads", true, "Number of threads to use");
        options.addOption("n", "number", true, "Number of requests to send from each thread");
        options.addOption("useragent", true, "User-Agent to use");
        options.addOption("requestMethod", true, "Request method to use (default: GET)");
        options.addOption("connectTimeout", true, "Connection timeout (default: 5000)");
    }


    /**
     * It parses the command line arguments and sets the corresponding variables,
     * and if there are any errors, it prints the help and exits.
     * and if the arguments have a help or version flag, it prints the info and exits.
     *
     * @param args the command line arguments
     */
    public void process(final String[] args) {
        try {
            final var commandLine = new DefaultParser().parse(options, args);
            if (commandLine.hasOption("help")) {
                printHelp(0);
            } else if (commandLine.hasOption("version")) {
                printVersion();
            }

            if (commandLine.hasOption("url"))
                Requester.setUrl(commandLine.getOptionValue("url"));
            if (commandLine.hasOption("threads"))
                MainController.setThreadsNum(commandLine.getOptionValue("threads"));
            if (commandLine.hasOption("number"))
                Requester.setReqNumber(commandLine.getOptionValue("number"));
            if (commandLine.hasOption("connectTimeout"))
                Requester.setConnectTimeout(commandLine.getOptionValue("connectTimeout"));
            if (commandLine.hasOption("useragent"))
                Requester.setUserAgent(commandLine.getOptionValue("useragent"));
            if (commandLine.hasOption("requestMethod"))
                Requester.setRequestMethod(commandLine.getOptionValue("requestMethod"));

        } catch (final ParseException | FieldException e) {
            // If an error occurs, print the error message and a help message and exit.
            System.err.println("Error: " + e.getMessage());
            printHelp(1);
        }
    }

    /**
     * If the user types in the command line argument -v, this method will be called.
     */
    private void printVersion() {
        System.out.println("Version: " + MainController.VERSION);
        System.exit(0);
    }

    /**
     * Prints the help message and exits the program.
     *
     * @param exitCode The exit code to use when exiting the application.
     */
    private void printHelp(final int exitCode) {
        new HelpFormatter().printHelp("java -jar jdosattacker.jar [options]", options);
        System.exit(exitCode);
    }
}
