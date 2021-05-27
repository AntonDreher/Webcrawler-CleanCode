package com.cleancode.webcrawler.page;

import java.net.URL;

public class FakePageFactory implements PageFactory {
    @Override
    public Page getPageWithStatistics(URL url) {
        return new FakeValidPage();
    }

    @Override
    public Page getInvalidPage() {
        return new InvalidPage();
    }
}
