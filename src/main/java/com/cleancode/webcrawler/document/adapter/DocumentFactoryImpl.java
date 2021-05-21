package com.cleancode.webcrawler.document.adapter;

import com.cleancode.webcrawler.document.Document;
import com.cleancode.webcrawler.document.DocumentFactory;
import org.jsoup.helper.HttpConnection;

import java.io.IOException;
import java.net.URL;

public class DocumentFactoryImpl implements DocumentFactory {
    @Override
    public Document getDocumentFrom(URL url) throws IOException {
        return new DocumentAdapter(HttpConnection.connect(url).get());
    }
}
