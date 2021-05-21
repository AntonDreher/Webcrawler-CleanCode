package com.cleancode.webcrawler;

import com.cleancode.webcrawler.document.HttpStatusException;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WebCrawler {
    private static final int HTTP_NOT_FOUND_STATUS = 404;

    private final URL startUrl;
    private final int maxDepth;

    private final Set<URL> visitedUrls = new HashSet<>();
    private final List<URL> notFoundUrls = new ArrayList<>();
    private final List<Page> pages = new ArrayList<>();

    public WebCrawler(URL startUrl) {
        this.startUrl = startUrl;
        this.maxDepth = 2;
    }

    public WebCrawler(URL startUrl, int maxDepth) {
        this.startUrl = startUrl;
        this.maxDepth = maxDepth;
    }

    public void crawl() {
        crawlRecursive(startUrl, 0);
    }

    boolean hasExceededMaxDepth(int depth) {
        return depth > maxDepth;
    }

    boolean isVisitedUrl(URL url) {
        return visitedUrls.contains(url);
    }

    private boolean isNotFoundError(HttpStatusException exception) {
        return exception.getStatusCode() == HTTP_NOT_FOUND_STATUS;
    }

    Page getPage(URL url) {
        try {
            Page page = new Page(url);
            page.computePageStatistics();
            return page;
        } catch (HttpStatusException e) {
            if (isNotFoundError(e)) {
                notFoundUrls.add(url);
            }
        } catch (IOException e) {
        }
        return null;
    }

    private void crawlLinkedPages(Page page, int depth) {
        page.getLinkedUrls().forEach(
                (linkedUrl) -> crawlRecursive(linkedUrl, depth + 1)
        );
    }

    void crawlRecursive(URL url, int depth) {
        if (hasExceededMaxDepth(depth) || isVisitedUrl(url)) {
            return;
        }

        visitedUrls.add(url);

        Page page = getPage(url);
        if (page != null) {
            pages.add(page);
            crawlLinkedPages(page, depth);
        }
    }

    private void printValidPageStatsTo(PrintStream outputStream) {
        outputStream.printf("Crawler Stats%n%n");

        pages.forEach(
                (page) -> outputStream.println(page.toString())
        );
    }

    private void printBrokenLinksTo(PrintStream outputStream) {
        outputStream.printf("%nBroken Links%n%n");

        notFoundUrls.forEach(
                (url) -> outputStream.println(url.toString())
        );
    }

    public void printStatsTo(PrintStream outputStream) {
        printValidPageStatsTo(outputStream);
        printBrokenLinksTo(outputStream);
    }

    List<URL> getNotFoundUrls() {
        return notFoundUrls;
    }

    Set<URL> getVisitedUrls() {
        return visitedUrls;
    }
}
