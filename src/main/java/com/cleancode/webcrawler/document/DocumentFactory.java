package com.cleancode.webcrawler.document;

import java.io.IOException;
import java.net.URL;

public interface DocumentFactory {
    Document getDocumentFrom(URL url) throws IOException;
}
