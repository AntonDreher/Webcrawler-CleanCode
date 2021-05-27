package com.cleancode.webcrawler.document;

import static com.cleancode.webcrawler.testutil.TestingConstants.INVALID_URL_TO_TEST;
import static com.cleancode.webcrawler.testutil.TestingConstants.invalidTestUrl;

public class FakeGetDocumentException extends GetDocumentException {
    public FakeGetDocumentException() {
        super("FakeException", new HttpStatusException("Bad Request", 400, INVALID_URL_TO_TEST), invalidTestUrl);
    }
}
