package eu.ciechanowiec.throwable.operations;

import eu.ciechanowiec.throwable.Printer;
import org.tinylog.Logger;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author Herman Ciechanowiec
 */
class OperationsOnExceptions {

    public static void main(String[] args) throws InterruptedException {
        OperationsOnExceptions operations = new OperationsOnExceptions();
        operations.message();
        operations.cause();
        operations.truncateStackTrace();
        operations.replaceStackTrace();
        operations.suppressException();
    }

    private void message() {
        Printer.separateUsageCase("Exception message");
        Printer.printToConsole("Creating exceptions, one with a message and one without...");
        Exception exceptionWithoutMessage = new Exception();
        Exception exceptionWithMessage = new Exception("Custom message");
        Printer.printToConsole(String.format("""
                A message from the exception without the specified message:
                    %s""",
                exceptionWithoutMessage.getMessage()));
        Printer.printToConsole(String.format("""
                A message from the exception with the specified message:
                    %s""",
                exceptionWithMessage.getMessage()));
    }

    private void cause() {
        Printer.separateUsageCase("Exception with\na nested cause");
        Printer.printToConsole("Creating an exception with a nested cause exception...");
        ExceptionCause exceptionCauseToPass = new ExceptionCause("I'm a nested cause exception");
        Exception exceptionWithCause = new Exception("Exception with nested cause", exceptionCauseToPass);
        ExceptionCause exceptionCauseRetrieved = (ExceptionCause) exceptionWithCause.getCause();
        Printer.printToConsole(String.format("""
                               Message from the nested cause exception:%n\t%s""",
                               exceptionCauseRetrieved.getMessage()));
    }

    private void truncateStackTrace() throws InterruptedException {
        Printer.separateUsageCase("Truncating stack trace");
        StackOverflowError error = produceStackOverFlowError();
        Printer.printToConsole("Retrieving a raw stack trace from an error...");
        StackTraceElement[] stackTraceOriginal = error.getStackTrace();
        Printer.printToConsole("Raw stack trace size before truncating: " + stackTraceOriginal.length);
        StackTraceElement[] stackTraceTruncated = truncateStackTrace(stackTraceOriginal, 10);
        Printer.printToConsole("Raw stack trace size after truncating: " + stackTraceTruncated.length);
        Printer.printToConsole("Setting for the error the truncated stack trace...");
        error.setStackTrace(stackTraceTruncated);
        Printer.printToConsole("Logging the error with its new truncated stack trace...");
        TimeUnit.SECONDS.sleep(1);
        Logger.error(error);
    }

    private void replaceStackTrace() throws InterruptedException {
        Printer.separateUsageCase("Replacing stack trace");
        StackOverflowError error = produceStackOverFlowError();
        Printer.printToConsole("Creating a mocked stack trace...");
        StackTraceElement[] stackTraceMocked = new StackTraceElement[2];
        stackTraceMocked[0] = new StackTraceElement("MockedClassNameOne", "MockedFileNameOne",
                                           "MockedMethodNameOne", 1);
        stackTraceMocked[1] = new StackTraceElement("MockedClassNameTwo", "MockedFileNameTwo",
                                           "MockedMethodNameTwo", 2);
        Printer.printToConsole("Replacing the original stack trace with a mocked one...");
        error.setStackTrace(stackTraceMocked);
        Printer.printToConsole("Logging the error with its new mocked stack trace...");
        TimeUnit.SECONDS.sleep(1);
        Logger.error(error);
    }

    private void suppressException() throws InterruptedException {
        Printer.separateUsageCase("Suppressing exception");
        Printer.printToConsole("Creating a suppressed exception...");
        SuppressedException suppressedException = new SuppressedException("I'm a suppressed exception");
        Printer.printToConsole("Creating a wrapper-exception with the suppressed one inside...");
        Exception exceptionWithSuppressedOneInside = new Exception("I'm an exception with a suppressed one inside");
        exceptionWithSuppressedOneInside.addSuppressed(suppressedException);
        Printer.printToConsole("""
                               Retrieving the suppressed exception from its
                                   wrapper-exception and logging it...""");
        SuppressedException retrievedSuppressedException =
                (SuppressedException) exceptionWithSuppressedOneInside.getSuppressed()[0];
        TimeUnit.SECONDS.sleep(1);
        Logger.error(retrievedSuppressedException);
    }

    private StackOverflowError produceStackOverFlowError() {
        try {
            infiniteRecursion();
            return new StackOverflowError("Intended StackOverFlowError");
        } catch (StackOverflowError error) {
            return error;
        }
    }

    private StackTraceElement[] truncateStackTrace(StackTraceElement[] stackTraceToTruncate, int maxSize) {
        if (stackTraceToTruncate.length <= maxSize) {
            return stackTraceToTruncate;
        } else {
            return Arrays.copyOf(stackTraceToTruncate, maxSize);
        }
    }

    private void infiniteRecursion() {
        infiniteRecursion();
    }
}
