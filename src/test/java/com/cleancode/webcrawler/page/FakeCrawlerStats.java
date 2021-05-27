package com.cleancode.webcrawler.page;

import com.cleancode.webcrawler.stats.CrawlerStats;
import com.cleancode.webcrawler.document.FakeGetDocumentException;
import com.cleancode.webcrawler.document.GetDocumentException;

import java.net.URL;
import java.util.List;

import static com.cleancode.webcrawler.testutil.TestingConstants.*;

public class FakeCrawlerStats extends CrawlerStats {
    public FakeCrawlerStats(){
        super();
    }

    @Override
    public List<GetDocumentException> getCrawlingErrors() {
        return List.of(new FakeGetDocumentException());
    }

    @Override
    public List<Page> getPages() {
        return List.of(new FakePageFactory().getPageWithStatistics(validTestUrl));
    }

    @Override
    public List<URL> getNotFoundUrls() {
        return List.of(notFoundTestUrl);
    }
}
