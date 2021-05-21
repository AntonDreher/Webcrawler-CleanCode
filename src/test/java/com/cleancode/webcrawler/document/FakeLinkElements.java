package com.cleancode.webcrawler.document;

import java.util.List;

public class FakeLinkElements implements Elements {
    @Override
    public int size() {
        return 3;
    }

    @Override
    public List<String> eachAttr(String attributeKey) {
        return List.of("https://www.test.at", "relative/path", "https://mytest.org");
    }
}
