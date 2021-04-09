package com.cleancode.webcrawler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;


public class TestClassMain {
    public final static String validURL = "https://www.google.com/";
    public final static String invalidURL = "www...google.com";
    public final static String validPath = "/dev/null";
    public final static String invalidPath = "/x/y";

    private<T> void testSystemExitIsCalledWith(Consumer<T> function, T argument, int expectedStatus){
        SecurityManager initialSecurityManger = System.getSecurityManager();
        try {
            System.setErr(new PrintStream(OutputStream.nullOutputStream()));
            System.setSecurityManager(new NoExitSecurityManager());
            function.accept(argument);
            fail("Expected a call to System.exit()");
        } catch (ExitException e) {
            assertEquals(e.status, expectedStatus);
        } finally {
            System.setSecurityManager(initialSecurityManger);
            System.setErr(System.err);
        }
    }

    @Test
    public void testGetStartUrlFromArgsInvalidURLCallsSystemExit(){
        testSystemExitIsCalledWith(
                Main::getStartUrlFromArgs,
                new String[]{invalidURL},
                1
        );
    }

    @Test
    public void testGetPrintStreamFromArgsInvalidPathCallsSystemExit(){
        testSystemExitIsCalledWith(
                Main::getPrintStreamFromArgs,
                new String[]{validURL, invalidPath},
                1
        );
    }

    @Test
    public void testGetStartUrlFromArgsValidURL() throws MalformedURLException {
        Assertions.assertEquals(new URL(validURL), Main.getStartUrlFromArgs(new String[]{validURL}));
    }

    @Test
    public void testGetPrintStreamFromArgsNoPath(){
        Assertions.assertEquals(System.out, Main.getPrintStreamFromArgs(new String[]{validURL}));
    }

    @Test
    public void testGetPrintStreamFromArgsValidPath() {
        PrintStream printStream = Main.getPrintStreamFromArgs(new String[]{validURL, validPath});
        Assertions.assertNotNull(printStream);
    }

    @Test
    public void testValidateArgsLengthNoArguments() {
        testSystemExitIsCalledWith(
                Main::validateArgsLength,
                new String[]{},
                1
        );
    }

    @Test
    public void testValidateArgsLengthThreeArguments() {
        testSystemExitIsCalledWith(
                Main::validateArgsLength,
                new String[]{validURL, validPath, "extra"},
                1
        );
    }

    @Test
    public void testValidateArgsLengthTwoArguments() {
        assertDoesNotThrow(() -> Main.validateArgsLength(new String[]{validURL, validPath}));
    }
}
