package com.cleancode.webcrawler.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class URLResolver {
    public static URL getAbsoluteUrl(URL baseUrl, String uri) {
        try {
            if (new URI(uri).isAbsolute()) {
                return new URL(uri);
            } else {
                return new URL(baseUrl, uri);
            }
        } catch (MalformedURLException | URISyntaxException e) {
            return null;
        }
    }
}
