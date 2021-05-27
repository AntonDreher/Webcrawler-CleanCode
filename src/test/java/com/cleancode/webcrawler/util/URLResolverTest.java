package com.cleancode.webcrawler.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static com.cleancode.webcrawler.testutil.TestingConstants.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class URLResolverTest {
    public URL baseUrl;

    @BeforeEach
    public void init() {
        baseUrl = validTestUrl;
    }

    @Test
    public void testGetAbsoluteUrl() {
        String absoluteUrl = "https://www.wikipedia.com";
        assertEquals(absoluteUrl, URLResolver.getAbsoluteUrl(baseUrl, absoluteUrl).toString());
    }

    @Test
    public void testGetAbsoluteUrlWithRelativeUrl() {
        String relativeUrl = "relative/URL/";
        assertEquals(VALID_URL_TO_TEST + relativeUrl, URLResolver.getAbsoluteUrl(baseUrl, relativeUrl).toString());
    }

    @Test
    public void testGetAbsoluteUrlInvalid() {
        String invalidURL = "htt://www...123...";
        assertNull(URLResolver.getAbsoluteUrl(baseUrl, invalidURL));
    }
}
