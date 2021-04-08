package com.cleancode.webcrawler;

import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Page {
    private URL url;
    private Document document;
    private PageStats stats;

    public Page(URL url) throws IOException {
        this.url = url;
        getDocument();
        this.stats = new PageStats(document);
    }

    URL getAbsoluteUrl(String uri){
        try {
            if (new URI(uri).isAbsolute()){
                return new URL(uri);
            } else {
                return new URL(url, uri);
            }
        } catch (MalformedURLException | URISyntaxException e) {
            return null;
        }
    }

    Set<URL> getLinkedUrls(){
        Elements links = document.select(PageStats.LINK_TAG + PageStats.EXCLUDE_SAME_PAGE_LINKS);
        List<String> uris = links.eachAttr("href");
        return uris.stream().map(this::getAbsoluteUrl).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    private void getDocument() throws IOException {
        this.document = HttpConnection.connect(url).get();
    }

    void setDocument(Document document){
        this.document = document;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", url.toString(), stats.toString());
    }
}
