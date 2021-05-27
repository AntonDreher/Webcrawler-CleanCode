package com.cleancode.webcrawler.page;

import com.cleancode.webcrawler.document.Document;
import com.cleancode.webcrawler.document.Elements;
import com.cleancode.webcrawler.stats.PageStats;
import com.cleancode.webcrawler.util.URLResolver;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cleancode.webcrawler.util.HTMLConstants.*;

public class ValidPage implements Page {
    private final URL url;

    private Document document;
    private PageStats stats;

    public ValidPage(URL url, Document document) {
        super();
        this.url = url;
        this.document = document;
    }

    public void computePageStatistics() {
        this.stats = new PageStats(document);
    }

    public Set<URL> getLinkedUrls() {
        Elements links = document.selectCss(LINK_TAG + EXCLUDE_SAME_PAGE_LINKS);
        List<String> uris = links.eachAttr(LINK_REFERENCE_ATTRIBUTE);
        return uris.stream()
                .map((uri) -> URLResolver.getAbsoluteUrl(url, uri))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return String.format("%s: %s", url.toString(), stats.toString());
    }
}
