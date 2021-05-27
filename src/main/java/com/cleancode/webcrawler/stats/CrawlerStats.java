package com.cleancode.webcrawler.stats;

import com.cleancode.webcrawler.document.GetDocumentException;
import com.cleancode.webcrawler.page.Page;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CrawlerStats {
    private final List<URL> notFoundUrls = new ArrayList<>();
    private final List<GetDocumentException> crawlingErrors = new ArrayList<>();
    private final List<Page> pages = new ArrayList<>();

    public void addPage(Page page) {
        pages.add(page);
    }

    public void addNotFoundUrl(URL url) {
        notFoundUrls.add(url);
    }

    public void addCrawlingError(GetDocumentException exception) {
        crawlingErrors.add(exception);
    }

    public void mergeWith(CrawlerStats stats) {
        this.pages.addAll(stats.pages);
        this.crawlingErrors.addAll(stats.crawlingErrors);
        this.notFoundUrls.addAll(stats.notFoundUrls);
    }

    public List<URL> getNotFoundUrls() {
        return notFoundUrls;
    }

    public List<GetDocumentException> getCrawlingErrors() {
        return crawlingErrors;
    }

    public List<Page> getPages() {
        return pages;
    }
}
