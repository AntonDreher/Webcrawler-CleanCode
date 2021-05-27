package com.cleancode.webcrawler.document;

import java.net.URL;

public interface DocumentFactory {
    Document getDocumentFrom(URL url);
}
