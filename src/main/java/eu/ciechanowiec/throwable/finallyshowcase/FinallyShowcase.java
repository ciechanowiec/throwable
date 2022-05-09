package eu.ciechanowiec.throwable.finallyshowcase;

import eu.ciechanowiec.throwable.Printer;
import org.tinylog.Logger;

import java.util.concurrent.TimeUnit;

/**
 * @author Herman Ciechanowiec
 */
class FinallyShowcase {

    public static void main(String[] args) throws InterruptedException {
        FinallyShowcase finallyShowcase = new FinallyShowcase();
        finallyShowcase.finallyWithExpectedException();
        finallyShowcase.finallyWithUnexpectedException();
        finallyShowcase.exceptionFromFinally();
    }

    private void finallyWithExpectedException() throws InterruptedException {
        Printer.separateUsageCase("'finally' usage with an\nexpected exception");
        try {
            Printer.printToConsole("Entered the 'try' block");
            Printer.printToConsole("Now an expected exception will be thrown...");
            throw new RuntimeException("Expected exception");
        } catch (RuntimeException exception) {
            Printer.printToConsole("""
                                   Entered the 'catch' block. Now the caught exception
                                       from the 'try' block will be logged...""");
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        } finally {
            TimeUnit.SECONDS.sleep(2);
            Printer.printToConsole("Entered the 'finally' block");
        }
    }

    private void finallyWithUnexpectedException() throws InterruptedException {
        Printer.separateUsageCase("'finally' usage with an\nunexpected exception");
        try {
            try {
                Printer.printToConsole("Entered the 'try' block");
                Printer.printToConsole("""
                                       Now an unexpected exception will be thrown. It will
                                           not be caught and will be propagated to the
                                           caller. However, prior to that, the 'finally'
                                           block will be executed...""");
                /* The following exception will crash the program,
                 * but 'finally' block will be executed anyway: */
                throw new IndexOutOfBoundsException("Unexpected exception");
            } catch (ArithmeticException exception) {
                // This block will not be executed
                Printer.printToConsole("""
                                       Entered the 'catch' block. Now the caught exception
                                           from the 'try' block will be logged...""");
                TimeUnit.SECONDS.sleep(1);
                Printer.printToConsole("Expected exception was caught");
                Logger.error(exception);
            } finally {
                TimeUnit.SECONDS.sleep(2);
                Printer.printToConsole("""
                                       Entered the 'finally' block. Now the unexpected
                                           exception from the 'try' block will be
                                           propagated to the caller...""");
            }
        } catch (IndexOutOfBoundsException exception) {
            Printer.printToConsole("""
                                   Now the program got back to the caller of the 'try-catch-finally'
                                       block, which received the unexpected exception thrown
                                       from the 'try' block and will handle it by logging...""");
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }

    private void exceptionFromFinally() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        Printer.separateUsageCase("exception from\nthe 'finally' block");
        try {
            try {
                Printer.printToConsole("Entered the 'try' block");
                Printer.printToConsole("Throwing an expected exception...");
                throw new IndexOutOfBoundsException("Expected exception from the 'try' block");
            } catch (IndexOutOfBoundsException exception) {
                Printer.printToConsole("""
                                       Entered the 'catch' block. Now the caught exception
                                           from the 'try' block will be logged...""");
                TimeUnit.SECONDS.sleep(1);
                Logger.error(exception);
            } finally {
                TimeUnit.SECONDS.sleep(1);
                Printer.printToConsole("""
                                       Entered the 'finally' block. Now an exception will be thrown.
                                           That exception will be propagated to the caller...""");
                throw new ArithmeticException("Expected exception from the 'finally' block");
            }
        } catch (ArithmeticException exception) {
            Printer.printToConsole("""
                                   Now the program got back to the caller of the
                                       'try-catch-finally' block, which received the
                                       exception thrown from the 'finally' block and
                                       will handle it by logging...""");
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }

    }
}
