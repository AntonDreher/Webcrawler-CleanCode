package com.cleancode.webcrawler.page;


import java.net.URL;
import java.util.Collections;
import java.util.Set;

public class InvalidPage implements Page {

    @Override
    public void computePageStatistics() {
    }

    @Override
    public Set<URL> getLinkedUrls() {
        return Collections.emptySet();
    }
}
