package com.cleancode.webcrawler.document;

import java.util.Collections;
import java.util.List;

public class FakeEmptyElements implements Elements {
    @Override
    public int size() {
        return 0;
    }

    @Override
    public List<String> eachAttr(String attributeKey) {
        return Collections.emptyList();
    }
}
