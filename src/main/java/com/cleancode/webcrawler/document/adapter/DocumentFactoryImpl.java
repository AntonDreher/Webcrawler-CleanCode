package com.cleancode.webcrawler.document.adapter;

import com.cleancode.webcrawler.document.Document;
import com.cleancode.webcrawler.document.DocumentFactory;
import com.cleancode.webcrawler.document.GetDocumentException;
import com.cleancode.webcrawler.document.Http404StatusException;
import org.jsoup.HttpStatusException;
import org.jsoup.helper.HttpConnection;

import java.io.IOException;
import java.net.URL;

public class DocumentFactoryImpl implements DocumentFactory {
    @Override
    public Document getDocumentFrom(URL url) {
        try {
            return new DocumentAdapter(HttpConnection.connect(url).get());
        } catch (HttpStatusException e) {
            if (e.getStatusCode() == 404){
                throw new Http404StatusException(e.getMessage(), e.getUrl());
            }
            throw new GetDocumentException(e.getMessage(), e, url);
        } catch (IOException e){
            throw new GetDocumentException(e.getMessage(), e, url);
        }
    }
}
