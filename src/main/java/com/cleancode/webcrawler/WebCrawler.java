package com.cleancode.webcrawler;

import com.cleancode.webcrawler.document.GetDocumentException;
import com.cleancode.webcrawler.document.Http404StatusException;
import com.cleancode.webcrawler.page.Page;
import com.cleancode.webcrawler.page.PageFactory;
import com.cleancode.webcrawler.stats.CrawlerStats;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public class WebCrawler implements Callable<CrawlerStats> {
    private static PageFactory pageFactory;
    private final URL startUrl;
    private final int maxDepth;
    private final Set<URL> visitedUrls = new HashSet<>();
    private final CrawlerStats crawlerStats = new CrawlerStats();

    public WebCrawler(URL startUrl) {
        this.startUrl = startUrl;
        this.maxDepth = 2;
    }

    public WebCrawler(URL startUrl, int maxDepth) {
        this.startUrl = startUrl;
        this.maxDepth = maxDepth;
    }

    public static void setPageFactory(PageFactory pageFactory) {
        WebCrawler.pageFactory = pageFactory;
    }

    @Override
    public CrawlerStats call() {
        crawl();
        return crawlerStats;
    }

    public void crawl() {
        crawlRecursive(startUrl, 0);
    }

    private void crawlLinkedPages(Page page, int depth) {
        page.getLinkedUrls().forEach(
                (linkedUrl) -> crawlRecursive(linkedUrl, depth + 1)
        );
    }

    Page getPage(URL url) {
        try {
            Page page = pageFactory.getPageWithStatistics(url);
            crawlerStats.addPage(page);
            return page;
        } catch (Http404StatusException e) {
            crawlerStats.addNotFoundUrl(url);
        } catch (GetDocumentException e) {
            crawlerStats.addCrawlingError(e);
        }
        return pageFactory.getInvalidPage();
    }

    void crawlRecursive(URL url, int depth) {
        if (hasExceededMaxDepth(depth) || isVisitedUrl(url)) {
            return;
        }

        visitedUrls.add(url);

        Page page = getPage(url);
        crawlLinkedPages(page, depth);
    }

    public List<URL> getNotFoundUrls() {
        return crawlerStats.getNotFoundUrls();
    }

    boolean hasExceededMaxDepth(int depth) {
        return depth > maxDepth;
    }

    boolean isVisitedUrl(URL url) {
        return visitedUrls.contains(url);
    }

    Set<URL> getVisitedUrls() {
        return visitedUrls;
    }

    public CrawlerStats getCrawlerStats() {
        return crawlerStats;
    }
}
