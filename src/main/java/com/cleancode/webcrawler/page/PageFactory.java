package com.cleancode.webcrawler.page;

import java.net.URL;

public interface PageFactory {
    Page getPageWithStatistics(URL url);

    Page getInvalidPage();
}
