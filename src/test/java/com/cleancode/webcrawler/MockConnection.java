package com.cleancode.webcrawler;

import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

public class MockConnection {
    public static MockedStatic<HttpConnection> httpConnectionMockedStatic = mockStatic(HttpConnection.class);
    public static Set<MockConnection> mockConnections = new HashSet<>();

    public URL url;
    public HttpConnection connection = mock(HttpConnection.class);

    public MockConnection(String url) throws IOException {
        this.url = new URL(url);
        mockConnections.add(this);
        httpConnectionMockedStatic.when(() -> HttpConnection.connect(this.url)).thenReturn(connection);
    }

    public void setDocument(Document document) throws IOException {
        when(connection.get()).thenReturn(document);
    }

    public void setThrows(Throwable throwable) throws IOException {
        when(connection.get()).thenThrow(throwable);
    }

    public static void addMockConnectionToEmptyDocument(String url) throws IOException {
        MockConnection connection = new MockConnection(url);
        mockConnections.add(connection);
        connection.setDocument(Document.createShell(url));
    }
}
