package com.cleancode.webcrawler.document;

import java.util.Collections;
import java.util.List;

public class FakeImageElements implements Elements {
    @Override
    public int size() {
        return 2;
    }

    @Override
    public List<String> eachAttr(String attributeKey) {
        return Collections.emptyList();
    }
}
