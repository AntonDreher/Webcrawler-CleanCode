package com.cleancode.webcrawler.page;

import java.net.URL;
import java.util.Set;

public interface Page {
    void computePageStatistics();

    Set<URL> getLinkedUrls();
}
