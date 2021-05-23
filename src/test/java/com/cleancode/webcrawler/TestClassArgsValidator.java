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


public class TestClassArgsValidator {
    public final static String validURLGoogle = "https://www.google.com/";
    public final static String validURLWikipedia = "https://www.wikipedia.com";
    public final static String invalidURL = "www...google.com";
    public final static String validPath = System.getProperty("os.name").startsWith("Windows") ? "NUL" : "/dev/null";
    public final static String invalidPath = "/x/y/te?t";


    private <T> void testSystemExitIsCalledWith(Runnable function, int expectedStatus) {
        SecurityManager initialSecurityManger = System.getSecurityManager();
        try {
            System.setErr(new PrintStream(OutputStream.nullOutputStream()));
            System.setSecurityManager(new NoExitSecurityManager());
            function.run();
            fail("Expected a call to System.exit()");
        } catch (ExitException e) {
            assertEquals(e.status, expectedStatus);
        } finally {
            System.setSecurityManager(initialSecurityManger);
            System.setErr(System.err);
        }
    }

    private <T> void testNoSystemExitIsCalled(Runnable function) {
        SecurityManager initialSecurityManger = System.getSecurityManager();
        try {
            System.setErr(new PrintStream(OutputStream.nullOutputStream()));
            System.setSecurityManager(new NoExitSecurityManager());
            function.run();
        } catch (ExitException e) {
            fail("Unexpected call to System.exit()");
        } finally {
            System.setErr(System.err);
            System.setSecurityManager(initialSecurityManger);
        }
    }

    @Test
    public void testGetUrlsToCrawlFromArgsInvalidURLCallsSystemExit() {
        ArgsValidator.setArgumentsToCheck(new String[]{invalidURL});
        testSystemExitIsCalledWith(
                ArgsValidator::getUrlsToCrawlFromArgs,
                1
        );
    }

    @Test
    public void testGetUrlsToCrawlFromArgsDuplicatedURLCallsSystemExit(){
        ArgsValidator.setArgumentsToCheck(new String[]{validURLGoogle, validURLGoogle});
        testSystemExitIsCalledWith(
                ArgsValidator::getUrlsToCrawlFromArgs,
                1
        );
    }

    @Test
    public void testGetUrlsToCrawlFromArgsDuplicatedAndUniqueURLCallsSystemExit(){
        ArgsValidator.setArgumentsToCheck(new String[]{validURLGoogle, validURLWikipedia, validURLGoogle});
        testSystemExitIsCalledWith(
                ArgsValidator::getUrlsToCrawlFromArgs,
                1
        );
    }

    @Test
    public void testGetPrintStreamFromArgsInvalidPathCallsSystemExit() {
        ArgsValidator.setArgumentsToCheck(new String[]{validURLGoogle, invalidPath});
        testNoSystemExitIsCalled(
                ArgsValidator::getPrintStreamFromArgs
        );
    }

    @Test
    public void testGetUrlsToCrawlFromArgsValidURL() throws MalformedURLException {
        ArrayList<URL> validURLList = new ArrayList<>();
        validURLList.add(new URL(validURLGoogle));
        ArgsValidator.setArgumentsToCheck( new String[]{validURLGoogle});
        testNoSystemExitIsCalled(ArgsValidator::getUrlsToCrawlFromArgs);
        Assertions.assertEquals(validURLList, ArgsValidator.getUrlsToCrawlFromArgs());
    }

    @Test
    public void testGetPrintStreamFromArgsNoPathSingleURLS() {
        ArgsValidator.setArgumentsToCheck(new String[]{validURLGoogle});
        testNoSystemExitIsCalled(ArgsValidator::getPrintStreamFromArgs);
        Assertions.assertEquals(System.out, ArgsValidator.getPrintStreamFromArgs());
    }

    @Test
    public void testGetPrintStreamFromArgsNoPathMultipleURLS() {
        ArgsValidator.setArgumentsToCheck(new String[]{validURLGoogle, validURLWikipedia});
        testNoSystemExitIsCalled(ArgsValidator::getPrintStreamFromArgs);
        Assertions.assertEquals(System.out, ArgsValidator.getPrintStreamFromArgs());
    }

    @Test
    public void testGetPrintStreamFromArgsValidPath() {
        ArgsValidator.setArgumentsToCheck(new String[]{validURLGoogle, validPath});
        testNoSystemExitIsCalled(ArgsValidator::getPrintStreamFromArgs);
        Assertions.assertNotNull(ArgsValidator.getPrintStreamFromArgs());
    }

    @Test
    public void testValidateArgsLengthNoArguments() {
        ArgsValidator.setArgumentsToCheck(new String[]{});
        testSystemExitIsCalledWith(
                ArgsValidator::validateArgsLength,
                1
        );
    }

    @Test
    public void testValidateArgsLengthOneArguments() {
        ArgsValidator.setArgumentsToCheck(new String[]{validURLGoogle});
        testNoSystemExitIsCalled(ArgsValidator::validateArgsLength);
        assertDoesNotThrow(() -> ArgsValidator.validateArgsLength());
    }

    @Test
    public void testValidateArgsLengthTwoArguments() {
        ArgsValidator.setArgumentsToCheck(new String[]{validURLGoogle, validPath});
        testNoSystemExitIsCalled(ArgsValidator::validateArgsLength);
        assertDoesNotThrow(() -> ArgsValidator.validateArgsLength());
    }

    @Test
    public void testValidateArgsLengthThreeArguments() {
        ArgsValidator.setArgumentsToCheck(new String[]{validURLGoogle, validURLWikipedia, validPath});
        testNoSystemExitIsCalled(
                ArgsValidator::validateArgsLength
        );
        assertDoesNotThrow(() -> ArgsValidator.validateArgsLength());
    }

    @Test
    public void testIsLastParameterFilePathTwoArgumentsValidPathTrue(){
        ArgsValidator.setArgumentsToCheck(new String[]{validURLGoogle, validPath});
        testNoSystemExitIsCalled(ArgsValidator::isLastParameterFilePath);
        assertTrue(ArgsValidator.isLastParameterFilePath());
    }

    @Test
    public void testIsLastParameterFilePathThreeArgumentsValidPathTrue(){
        ArgsValidator.setArgumentsToCheck(new String[]{validURLGoogle, validURLWikipedia, validPath});
        testNoSystemExitIsCalled(ArgsValidator::isLastParameterFilePath);
        assertTrue(ArgsValidator.isLastParameterFilePath());
    }

    @Test
    public void testIsLastParameterFilePathTwoArgumentsFalse(){
        ArgsValidator.setArgumentsToCheck(new String[]{validURLGoogle, validURLWikipedia});
        testNoSystemExitIsCalled(ArgsValidator::isLastParameterFilePath);
        assertFalse(ArgsValidator.isLastParameterFilePath());
    }

    @Test
    public void testIsLastParameterFilePathThreeArgumentsValidPathFalse(){
        ArgsValidator.setArgumentsToCheck(new String[]{validURLGoogle, validPath, validURLWikipedia});
        testNoSystemExitIsCalled(ArgsValidator::isLastParameterFilePath);
        assertFalse(ArgsValidator.isLastParameterFilePath());
    }

    @Test
    public void testIsLastParameterFilePathTwoArgumentsInvalidPathFalse(){
        ArgsValidator.setArgumentsToCheck(new String[]{validURLGoogle, invalidPath});
        testNoSystemExitIsCalled(ArgsValidator::isLastParameterFilePath);
        assertFalse(ArgsValidator.isLastParameterFilePath());
    }
}
