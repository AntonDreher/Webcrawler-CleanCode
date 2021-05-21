package com.cleancode.webcrawler.document.adapter;

import com.cleancode.webcrawler.document.HttpStatusException;

public class HttpStatusExceptionAdapter extends HttpStatusException {
    public HttpStatusExceptionAdapter(org.jsoup.HttpStatusException exception) {
        super(exception.getMessage(), exception.getStatusCode(), exception.getUrl());
    }
}
