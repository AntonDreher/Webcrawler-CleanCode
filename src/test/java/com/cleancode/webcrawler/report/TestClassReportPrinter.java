package com.cleancode.webcrawler.report;

import com.cleancode.webcrawler.page.FakeCrawlerStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.cleancode.webcrawler.testutil.TestingConstants.validTestUrl;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestClassReportPrinter {
    private ReportPrinter printer;

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void init() {
        outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        printer = new ReportPrinter(printStream);
    }

    @Test
    public void testPrintValidPageStats() {
        printer.printValidPageStats(new FakeCrawlerStats());
        String expectedOutput = String.format(
                "Report%n%n%s: 11 word(s), 3 link(s), 2 image(s), 1 video(s)%n", validTestUrl
        );
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testPrintBrokenLinks() {
        printer.printBrokenLinks(new FakeCrawlerStats());
        String expectedOutput = String.format(
                "%nBroken Links%n%n%s%n", validTestUrl + "404"
        );
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testPrintErrors() {
        printer.printErrors(new FakeCrawlerStats());
        String expectedOutput = String.format(
                "%nErrors%n%n%s400: FakeException caused by HTTP 400 Bad Request%n", validTestUrl
        );
        assertEquals(expectedOutput, outputStream.toString());
    }
}
