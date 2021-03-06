package com.cleancode.webcrawler.document;

import java.net.URL;

public class GetDocumentException extends RuntimeException {
    private final URL url;

    public GetDocumentException(String message, Throwable cause, URL url) {
        super(message, cause);
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return String.format("%s: %s",
                url.toString(),
                getMessage()
        );
    }
}
