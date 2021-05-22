package com.cleancode.webcrawler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;


public class TestClassMain {
    public final static String validURLGoogle = "https://www.google.com/";
    public final static String validURLWikipedia = "https://www.wikipedia.com";
    public final static String invalidURL = "www...google.com";
    public final static String validPath = System.getProperty("os.name").startsWith("Windows") ? "NUL" : "/dev/null";
    public final static String invalidPath = "/x/y/te?t";

    private <T> void testSystemExitIsCalledWith(Consumer<T> function, T argument, int expectedStatus) {
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

    private <T> void testNoSystemExitIsCalled(Consumer<T> function, T argument) {
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
    public void testGetUrlsToCrawlFromArgsInvalidURLCallsSystemExit() {
        testSystemExitIsCalledWith(
                Main::getUrlsToCrawlFromArgs,
                new String[]{invalidURL},
                1
        );
    }

    @Test
    public void testGetUrlsToCrawlFromArgsDuplicatedURLCallsSystemExit(){
        testSystemExitIsCalledWith(
                Main::getUrlsToCrawlFromArgs,
                new String[]{validURLGoogle, validURLGoogle},
                1
        );
    }

    @Test
    public void testGetUrlsToCrawlFromArgsDuplicatedAndUniqueURLCallsSystemExit(){
        testSystemExitIsCalledWith(
                Main::getUrlsToCrawlFromArgs,
                new String[]{validURLGoogle, validURLWikipedia, validURLGoogle},
                1
        );
    }

    @Test
    public void testGetPrintStreamFromArgsInvalidPathCallsSystemExit() {
        testNoSystemExitIsCalled(
                Main::getPrintStreamFromArgs,
                new String[]{validURLGoogle, invalidPath}
        );
    }

    @Test
    public void testgetUrlsToCrawlFromArgsValidURL() throws MalformedURLException {
        ArrayList<URL> validURLList = new ArrayList<URL>();
        validURLList.add(new URL(validURLGoogle));
        testNoSystemExitIsCalled(Main::getUrlsToCrawlFromArgs, new String[]{validURLGoogle});
        Assertions.assertEquals(validURLList, Main.getUrlsToCrawlFromArgs(new String[]{validURLGoogle}));
    }

    @Test
    public void testGetPrintStreamFromArgsNoPathSingleURLS() {
        testNoSystemExitIsCalled(Main::getPrintStreamFromArgs, new String[]{validURLGoogle});
        Assertions.assertEquals(System.out, Main.getPrintStreamFromArgs(new String[]{validURLGoogle}));
    }

    @Test
    public void testGetPrintStreamFromArgsNoPathMultipleURLS() {
        testNoSystemExitIsCalled(Main::getPrintStreamFromArgs, new String[]{validURLGoogle, validURLWikipedia});
        Assertions.assertEquals(System.out, Main.getPrintStreamFromArgs(new String[]{validURLGoogle, validURLWikipedia}));
    }

    @Test
    public void testGetPrintStreamFromArgsValidPath() {
        testNoSystemExitIsCalled(Main::getPrintStreamFromArgs, new String[]{validURLGoogle, validPath});
        Assertions.assertNotNull(Main.getPrintStreamFromArgs(new String[]{validURLGoogle, validPath}));
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
        testNoSystemExitIsCalled(Main::validateArgsLength, new String[]{validURLGoogle});
        assertDoesNotThrow(() -> Main.validateArgsLength(new String[]{validURLGoogle}));
    }

    @Test
    public void testValidateArgsLengthTwoArguments() {
        testNoSystemExitIsCalled(Main::validateArgsLength, new String[]{validURLGoogle, validPath});
        assertDoesNotThrow(() -> Main.validateArgsLength(new String[]{validURLGoogle, validPath}));
    }

    @Test
    public void testValidateArgsLengthThreeArguments() {
        testNoSystemExitIsCalled(
                Main::validateArgsLength,
                new String[]{validURLGoogle, validURLWikipedia, validPath}
        );
        assertDoesNotThrow(() -> Main.validateArgsLength(new String[]{validURLGoogle, validPath}));
    }

    @Test
    public void testIsLastParameterFilePathTwoArgumentsValidPathTrue(){
        String[] argsToTest = {validURLGoogle, validPath};
        testNoSystemExitIsCalled(Main::isLastParameterFilePath, argsToTest);
        assertTrue(Main.isLastParameterFilePath(argsToTest));
    }

    @Test
    public void testIsLastParameterFilePathThreeArgumentsValidPathTrue(){
        String[] argsToTest = {validURLGoogle, validURLWikipedia, validPath};
        testNoSystemExitIsCalled(Main::isLastParameterFilePath, argsToTest);
        assertTrue(Main.isLastParameterFilePath(argsToTest));
    }

    @Test
    public void testIsLastParameterFilePathTwoArgumentsFalse(){
        String[] argsToTest = {validURLGoogle, validURLWikipedia};
        testNoSystemExitIsCalled(Main::isLastParameterFilePath, argsToTest);
        assertFalse(Main.isLastParameterFilePath(argsToTest));
    }

    @Test
    public void testIsLastParameterFilePathThreeArgumentsValidPathFalse(){
        String[] argsToTest = {validURLGoogle, validPath, validURLWikipedia};
        testNoSystemExitIsCalled(Main::isLastParameterFilePath, argsToTest);
        assertFalse(Main.isLastParameterFilePath(argsToTest));
    }

    @Test
    public void testIsLastParameterFilePathTwoArgumentsInvalidPathFalse(){
        String[] argsToTest = {validURLGoogle, invalidPath};
        testNoSystemExitIsCalled(Main::isLastParameterFilePath, argsToTest);
        assertFalse(Main.isLastParameterFilePath(argsToTest));
    }
}
