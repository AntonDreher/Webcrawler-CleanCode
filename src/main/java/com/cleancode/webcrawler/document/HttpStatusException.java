package com.cleancode.webcrawler.document;

public class HttpStatusException extends RuntimeException {
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
        return "HTTP " + statusCode + " - " + getMessage();
    }
}
