package com.cleancode.webcrawler.page;

import com.cleancode.webcrawler.document.FakeGetDocumentException;

import java.net.URL;

public class Fake400PageFactory implements PageFactory {
    @Override
    public Page getPageWithStatistics(URL url) {
        throw new FakeGetDocumentException();
    }

    @Override
    public Page getInvalidPage() {
        return new InvalidPage();
    }
}
