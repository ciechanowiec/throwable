package eu.ciechanowiec.throwable.misc;

import eu.ciechanowiec.throwable.Printer;
import org.tinylog.Logger;

import java.util.concurrent.TimeUnit;

/**
 * @author Herman Ciechanowiec
 */
class MiscShowcase {

    public static void main(String[] args) throws InterruptedException {
        MiscShowcase miscShowcase = new MiscShowcase();
        miscShowcase.exceptionInCatchBlockWithoutFinally();
        miscShowcase.exceptionInCatchBlockWithFinally();
        miscShowcase.returnInFinallyBlockWithUnexpectedException();
        miscShowcase.tryFinallyWithoutCatch();
    }

    private void exceptionInCatchBlockWithoutFinally() throws InterruptedException {
        Printer.separateUsageCase("""
                                  exception inside the 'catch'
                                  block without the 'finally' block""");
        try {
            try {
                Printer.printToConsole("Entered the 'try' block");
                Printer.printToConsole("Now an exception will be thrown...");
                throw new RuntimeException("Exception from the 'try' block");
            } catch (RuntimeException exception) {
                Printer.printToConsole("""
                                       Entered the 'catch' block. Now the exception
                                           from the 'try' block will be logged...""");
                TimeUnit.SECONDS.sleep(1);
                Logger.error(exception);
                Printer.printToConsole("""
                                       Now the 'catch' block will throw its own exception
                                           and propagate it to the caller...""");
                throw new RuntimeException("Exception from the 'catch' block");
            }
        } catch (RuntimeException exception) {
            Printer.printToConsole("""
                                   Now the program got back to the caller of the 'try-catch'
                                       block, which received the exception thrown from the
                                       'catch' block and will handle it by logging...""");
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }

    private void exceptionInCatchBlockWithFinally() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        Printer.separateUsageCase("""
                                  exception inside the 'catch'
                                  block with the 'finally' block""");
        try {
            try {
                Printer.printToConsole("Entered the 'try' block");
                Printer.printToConsole("Now an exception will be thrown...");
                throw new RuntimeException("Exception from the 'try' block");
            } catch (RuntimeException exception) {
                Printer.printToConsole("""
                                       Entered the 'catch' block. Now the exception
                                           from the 'try' block will be logged...""");
                TimeUnit.SECONDS.sleep(1);
                Logger.error(exception);
                Printer.printToConsole("""
                                       Now the 'catch' block will throw its own exception
                                           and propagate it to the caller. However, prior
                                           to that, the 'finally' block will be executed...""");
                throw new RuntimeException("Exception from the 'catch' block");
            } finally {
                Printer.printToConsole("""
                                       Entered the 'finally' block. It is executed
                                           although the previous 'catch' block has
                                           thrown its own exception...""");
            }
        } catch (RuntimeException exception) {
            Printer.printToConsole("""
                                   Now the program got back to the caller of the 'try-catch-finally'
                                       block, which received the exception thrown from the
                                       'catch' block and will handle it by logging...""");
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }

    private void returnInFinallyBlockWithUnexpectedException() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        Printer.separateUsageCase("'return' inside\nfinally block");
        try {
            Printer.printToConsole("Entered the 'try' block");
            Printer.printToConsole("Throwing an unexpected exception...");
            throw new ArithmeticException("Unexpected exception from the 'try' block");
        } catch (IndexOutOfBoundsException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
            throw new IllegalStateException("Unexpected exception from the 'catch' block");
        } finally {
            Printer.printToConsole("Entered the 'finally' block");
            TimeUnit.SECONDS.sleep(1);
            Printer.printToConsole("""
                                   Now the 'return' statement from the 'finally'
                                       block will be executed. That statement will
                                       cause that the unexpected exception from the
                                       'try' block will be swallowed. Without the
                                       'return' statement the unexpected exception
                                       would have been propagated to the caller...""");
            return;
        }
    }

    private void tryFinallyWithoutCatch() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        Printer.separateUsageCase("""
                                  'try-finally' block
                                  without 'catch' block""");
        try {
            try {
                Printer.printToConsole("Entered the 'try' block");
                Printer.printToConsole("Now an exception will be thrown...");
                throw new RuntimeException("Intended exception");
            } finally {
                Printer.printToConsole("""
                                       Entered 'finally' block after the exception was thrown.
                                           However, that exception wasn't handled and for that
                                           reason it will be propagated further to the caller""");
            }
        } catch (RuntimeException exception) {
            Printer.printToConsole("""
                                   Now the program got back to the caller of the 'try-finally'
                                       block (without 'catch' block), which received the exception
                                       thrown from that block and will handle it by logging""");
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }
}
