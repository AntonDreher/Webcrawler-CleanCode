package com.cleancode.webcrawler;

import com.cleancode.webcrawler.testutil.ExitException;
import com.cleancode.webcrawler.testutil.NoExitSecurityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
        ArgsValidator argsValidator = new ArgsValidator(new String[]{invalidURL});
        testSystemExitIsCalledWith(
                argsValidator::getUrlsToCrawlFromArgs,
                1
        );
    }

    @Test
    public void testGetUrlsToCrawlFromArgsDuplicatedURLCallsSystemExit() {
        ArgsValidator argsValidator = new ArgsValidator(new String[]{validURLGoogle, validURLGoogle});
        testSystemExitIsCalledWith(
                argsValidator::getUrlsToCrawlFromArgs,
                1
        );
    }

    @Test
    public void testGetUrlsToCrawlFromArgsDuplicatedAndUniqueURLCallsSystemExit() {
        ArgsValidator argsValidator = new ArgsValidator(new String[]{validURLGoogle, validURLWikipedia, validURLGoogle});
        testSystemExitIsCalledWith(
                argsValidator::getUrlsToCrawlFromArgs,
                1
        );
    }

    @Test
    public void testGetPrintStreamFromArgsInvalidPathCallsSystemExit() {
        ArgsValidator argsValidator = new ArgsValidator(new String[]{validURLGoogle, invalidPath});
        testNoSystemExitIsCalled(
                argsValidator::getPrintStreamFromArgs
        );
    }

    @Test
    public void testGetUrlsToCrawlFromArgsValidURL() throws MalformedURLException {
        ArrayList<URL> validURLList = new ArrayList<>();
        validURLList.add(new URL(validURLGoogle));
        ArgsValidator argsValidator = new ArgsValidator(new String[]{validURLGoogle});
        testNoSystemExitIsCalled(argsValidator::getUrlsToCrawlFromArgs);
        Assertions.assertEquals(validURLList, argsValidator.getUrlsToCrawlFromArgs());
    }

    @Test
    public void testGetPrintStreamFromArgsNoPathSingleURLS() {
        ArgsValidator argsValidator = new ArgsValidator(new String[]{validURLGoogle});
        testNoSystemExitIsCalled(argsValidator::getPrintStreamFromArgs);
        Assertions.assertEquals(System.out, argsValidator.getPrintStreamFromArgs());
    }

    @Test
    public void testGetPrintStreamFromArgsNoPathMultipleURLS() {
        ArgsValidator argsValidator = new ArgsValidator(new String[]{validURLGoogle, validURLWikipedia});
        testNoSystemExitIsCalled(argsValidator::getPrintStreamFromArgs);
        Assertions.assertEquals(System.out, argsValidator.getPrintStreamFromArgs());
    }

    @Test
    public void testGetPrintStreamFromArgsValidPath() {
        ArgsValidator argsValidator = new ArgsValidator(new String[]{validURLGoogle, validPath});
        testNoSystemExitIsCalled(argsValidator::getPrintStreamFromArgs);
        Assertions.assertNotNull(argsValidator.getPrintStreamFromArgs());
    }

    @Test
    public void testValidateArgsLengthNoArguments() {
        ArgsValidator argsValidator = new ArgsValidator(new String[]{});
        testSystemExitIsCalledWith(
                argsValidator::validateArgsLength,
                1
        );
    }

    @Test
    public void testValidateArgsLengthOneArguments() {
        ArgsValidator argsValidator = new ArgsValidator(new String[]{validURLGoogle});
        testNoSystemExitIsCalled(argsValidator::validateArgsLength);
        assertDoesNotThrow(argsValidator::validateArgsLength);
    }

    @Test
    public void testValidateArgsLengthTwoArguments() {
        ArgsValidator argsValidator = new ArgsValidator(new String[]{validURLGoogle, validPath});
        testNoSystemExitIsCalled(argsValidator::validateArgsLength);
        assertDoesNotThrow(argsValidator::validateArgsLength);
    }

    @Test
    public void testValidateArgsLengthThreeArguments() {
        ArgsValidator argsValidator = new ArgsValidator(new String[]{validURLGoogle, validURLWikipedia, validPath});
        testNoSystemExitIsCalled(
                argsValidator::validateArgsLength
        );
        assertDoesNotThrow(argsValidator::validateArgsLength);
    }

    @Test
    public void testIsLastParameterFilePathTwoArgumentsValidPathTrue() {
        ArgsValidator argsValidator = new ArgsValidator(new String[]{validURLGoogle, validPath});
        testNoSystemExitIsCalled(argsValidator::isLastParameterFilePath);
        assertTrue(argsValidator.isLastParameterFilePath());
    }

    @Test
    public void testIsLastParameterFilePathThreeArgumentsValidPathTrue() {
        ArgsValidator argsValidator = new ArgsValidator(new String[]{validURLGoogle, validURLWikipedia, validPath});
        testNoSystemExitIsCalled(argsValidator::isLastParameterFilePath);
        assertTrue(argsValidator.isLastParameterFilePath());
    }

    @Test
    public void testIsLastParameterFilePathTwoArgumentsFalse() {
        ArgsValidator argsValidator = new ArgsValidator(new String[]{validURLGoogle, validURLWikipedia});
        testNoSystemExitIsCalled(argsValidator::isLastParameterFilePath);
        assertFalse(argsValidator.isLastParameterFilePath());
    }

    @Test
    public void testIsLastParameterFilePathThreeArgumentsValidPathFalse() {
        ArgsValidator argsValidator = new ArgsValidator(new String[]{validURLGoogle, validPath, validURLWikipedia});
        testNoSystemExitIsCalled(argsValidator::isLastParameterFilePath);
        assertFalse(argsValidator.isLastParameterFilePath());
    }

    @Test
    public void testIsLastParameterFilePathTwoArgumentsInvalidPathFalse() {
        ArgsValidator argsValidator = new ArgsValidator(new String[]{validURLGoogle, invalidPath});
        testNoSystemExitIsCalled(argsValidator::isLastParameterFilePath);
        assertFalse(argsValidator.isLastParameterFilePath());
    }
}
