package com.cleancode.webcrawler.page;

import com.cleancode.webcrawler.document.Http404StatusException;

import java.net.URL;

public class Fake404PageFactory implements PageFactory {
    @Override
    public Page getPageWithStatistics(URL url) {
        throw new Http404StatusException("Not Found", url.toString());
    }

    @Override
    public Page getInvalidPage() {
        return new InvalidPage();
    }
}
