package com.cleancode.webcrawler.document;

public class Http404StatusException extends HttpStatusException {
    public Http404StatusException(String message, String url) {
        super(message, 404, url);
    }
}
