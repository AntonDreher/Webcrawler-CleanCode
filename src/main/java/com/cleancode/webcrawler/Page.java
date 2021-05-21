package com.cleancode.webcrawler;

import com.cleancode.webcrawler.document.Document;
import com.cleancode.webcrawler.document.DocumentFactory;
import com.cleancode.webcrawler.document.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cleancode.webcrawler.util.HTMLConstants.*;

public class Page {
    private static DocumentFactory documentFactory;

    private final URL url;
    private Document document;
    private PageStats stats;

    public Page(URL url) {
        this.url = url;
    }

    public static void setDocumentFactory(DocumentFactory documentFactory) {
        Page.documentFactory = documentFactory;
    }

    public void computePageStatistics() throws IOException {
        this.document = Page.documentFactory.getDocumentFrom(url);
        this.stats = new PageStats(document);
    }

    URL getAbsoluteUrl(String uri) {
        try {
            if (new URI(uri).isAbsolute()) {
                return new URL(uri);
            } else {
                return new URL(url, uri);
            }
        } catch (MalformedURLException | URISyntaxException e) {
            return null;
        }
    }

    Set<URL> getLinkedUrls() {
        Elements links = document.selectCss(LINK_TAG + EXCLUDE_SAME_PAGE_LINKS);
        List<String> uris = links.eachAttr(LINK_REFERENCE_ATTRIBUTE);
        return uris.stream()
                .map(this::getAbsoluteUrl)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return String.format("%s: %s", url.toString(), stats.toString());
    }
}
