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
    public final static String validPath = System.getProperty("os.name").startsWith("Windows") ? "NUL" : "/dev/null";
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

    private<T> void testNoSystemExitIsCalled(Consumer<T> function, T argument){
        SecurityManager initialSecurityManger = System.getSecurityManager();
        try {
            System.setErr(new PrintStream(OutputStream.nullOutputStream()));
            System.setSecurityManager(new NoExitSecurityManager());
            function.accept(argument);
        } catch (ExitException e) {
            fail("Unexpected call to System.exit()");
        } finally {
            System.setErr(System.err);
            System.setSecurityManager(initialSecurityManger);
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
        testNoSystemExitIsCalled(Main::getStartUrlFromArgs, new String[]{validURL});
        Assertions.assertEquals(new URL(validURL), Main.getStartUrlFromArgs(new String[]{validURL}));
    }

    @Test
    public void testGetPrintStreamFromArgsNoPath(){
        testNoSystemExitIsCalled(Main::getPrintStreamFromArgs, new String[]{validURL});
        Assertions.assertEquals(System.out, Main.getPrintStreamFromArgs(new String[]{validURL}));
    }

    @Test
    public void testGetPrintStreamFromArgsValidPath() {
        testNoSystemExitIsCalled(Main::getPrintStreamFromArgs, new String[]{validURL, validPath});
        Assertions.assertNotNull(Main.getPrintStreamFromArgs(new String[]{validURL, validPath}));
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
    public void testValidateArgsLengthOneArguments() {
        testNoSystemExitIsCalled(Main::validateArgsLength, new String[]{validURL});
        assertDoesNotThrow(() -> Main.validateArgsLength(new String[]{validURL}));
    }

    @Test
    public void testValidateArgsLengthTwoArguments() {
        testNoSystemExitIsCalled(Main::validateArgsLength, new String[]{validURL, validPath});
        assertDoesNotThrow(() -> Main.validateArgsLength(new String[]{validURL, validPath}));
    }

    @Test
    public void testValidateArgsLengthThreeArguments() {
        testSystemExitIsCalledWith(
                Main::validateArgsLength,
                new String[]{validURL, validPath, "extra"},
                1
        );
    }
}
