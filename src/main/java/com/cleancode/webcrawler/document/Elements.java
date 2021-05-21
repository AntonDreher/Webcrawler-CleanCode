package com.cleancode.webcrawler.document;

import java.util.List;

public interface Elements {
    int size();

    List<String> eachAttr(String attributeKey);
}
