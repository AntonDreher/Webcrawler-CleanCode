package com.cleancode.webcrawler.page;

import java.net.URL;
import java.util.Set;

import static com.cleancode.webcrawler.testutil.TestingConstants.linkedTestUrl;

public class FakeValidPage implements Page {
    @Override
    public void computePageStatistics() {
    }

    @Override
    public Set<URL> getLinkedUrls() {
        return Set.of(linkedTestUrl);
    }

    @Override
    public String toString() {
        return "https://www.google.com/: 11 word(s), 3 link(s), 2 image(s), 1 video(s)";
    }
}
