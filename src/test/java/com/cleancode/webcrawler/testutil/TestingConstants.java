package com.cleancode.webcrawler.testutil;

import java.net.MalformedURLException;
import java.net.URL;

public class TestingConstants {
    public static final String VALID_URL_TO_TEST = "https://www.google.com/";
    public static final String NOT_FOUND_URL_TO_TEST = "https://www.google.com/404";
    public static final String INVALID_URL_TO_TEST = "https://www.google.com/400";
    public static final String LINKED_URL_TO_TEST = "https://www.test.at";

    public static URL validTestUrl;
    public static URL notFoundTestUrl;
    public static URL invalidTestUrl;
    public static URL linkedTestUrl;

    static {
        try {
            validTestUrl = new URL(VALID_URL_TO_TEST);
            notFoundTestUrl = new URL(NOT_FOUND_URL_TO_TEST);
            invalidTestUrl = new URL(INVALID_URL_TO_TEST);
            linkedTestUrl = new URL(LINKED_URL_TO_TEST);
        } catch (MalformedURLException e) {
            // For the test URLs this will never occur so we will ignore it
        }
    }
}
