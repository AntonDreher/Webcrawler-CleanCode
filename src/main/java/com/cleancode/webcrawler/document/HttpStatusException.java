package com.cleancode.webcrawler.document;

import java.io.IOException;

public class HttpStatusException extends IOException {
    private final int statusCode;
    private final String url;

    public HttpStatusException(String message, int statusCode, String url) {
        super(message);
        this.statusCode = statusCode;
        this.url = url;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return super.toString() + ". Status=" + statusCode + ", URL=" + url;
    }
}