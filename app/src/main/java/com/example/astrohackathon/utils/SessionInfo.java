package com.example.astrohackathon.utils;

/**
 * Created by RoMY on 10/12/2014.
 */
public class SessionInfo {
    private static String accessToken;

    private SessionInfo() {

    }

    public static void setAccessToken(String accessToken) {
        SessionInfo.accessToken = accessToken;
    }

    public static String getAccessToken() {
        return SessionInfo.accessToken;
    }
}
