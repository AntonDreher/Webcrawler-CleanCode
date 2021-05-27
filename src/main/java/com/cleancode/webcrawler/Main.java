package com.cleancode.webcrawler;

import com.cleancode.webcrawler.document.adapter.DocumentFactoryImpl;
import com.cleancode.webcrawler.page.PageFactory;
import com.cleancode.webcrawler.page.PageFactoryImpl;
import com.cleancode.webcrawler.report.ReportPrinter;
import com.cleancode.webcrawler.stats.CrawlerStats;

import java.io.PrintStream;
import java.net.URL;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ArgsValidator argsValidator = new ArgsValidator(args);
        argsValidator.validateArgsLength();
        List<URL> urlsToCrawl = argsValidator.getUrlsToCrawlFromArgs();
        PrintStream output = argsValidator.getPrintStreamFromArgs();
        PageFactory pageFactory = new PageFactoryImpl(new DocumentFactoryImpl());
        WebCrawler.setPageFactory(pageFactory);
        CrawlerStats crawlerStats = crawlUrls(urlsToCrawl);
        ReportPrinter printer = new ReportPrinter(output);
        printer.printAllStats(crawlerStats);
    }

    private static CrawlerStats crawlUrls(List<URL> urlsToCrawl) {
        WebCrawlerScheduler scheduler = new WebCrawlerScheduler(urlsToCrawl);
        scheduler.startWebcrawlers();
        scheduler.waitForWebcrawlersToFinish();
        return scheduler.getCombinedCrawlerStats();
    }
}
