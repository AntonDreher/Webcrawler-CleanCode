package com.cleancode.webcrawler.page;

import com.cleancode.webcrawler.document.*;

import java.net.URL;

public class PageFactoryImpl implements PageFactory {
    private final DocumentFactory documentFactory;

    public PageFactoryImpl(DocumentFactory documentFactory) {
        this.documentFactory = documentFactory;
    }

    public Page getPageWithStatistics(URL url) {
        Page page = new ValidPage(url, documentFactory.getDocumentFrom(url));
        page.computePageStatistics();
        return page;
    }

    public Page getInvalidPage(){
        return new InvalidPage();
    }
}
