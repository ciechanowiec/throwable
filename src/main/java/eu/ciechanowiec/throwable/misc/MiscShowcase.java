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
        miscShowcase.exceptionInCatchBlock();
        miscShowcase.returnInFinallyBlock();
        miscShowcase.tryFinallyWithoutCatch();
    }

    private void exceptionInCatchBlock() throws InterruptedException {
        Printer.separateUsageCase("""
                                  exception inside
                                  the 'catch' block""");
        try {
            try {
                Printer.printToConsole("Entered the 'try' block");
                Printer.printToConsole("Now an exception will be thrown...");
                throw new RuntimeException("Exception from the 'try' block");
            } catch (RuntimeException exception) {
                TimeUnit.SECONDS.sleep(1);
                Printer.printToConsole("""
                                       Entered the 'catch' block. Now the exception 
                                           from the 'try' block will be logged...""");
                Logger.error(exception);
                Printer.printToConsole("""
                                       Now the 'catch' block will throw its own exception
                                           and propagate it to the caller...""");
                throw new RuntimeException("Exception from the 'catch' block");
            }
        } catch (RuntimeException exception) {
            Printer.printToConsole("""
                                   Now the program got back to the caller of 'try-catch'
                                       block, which received the exception thrown from the
                                       'catch' block and will handle it by logging...""");
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }

    private void returnInFinallyBlock() throws InterruptedException {
        Printer.separateUsageCase("'return' inside\nfinally block");
        try {
            Printer.printToConsole("Entered the 'try' block");
            Printer.printToConsole("Throwing an unexpected exception...");
            throw new ArithmeticException("Unexpected exception");
        } catch (IndexOutOfBoundsException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        } finally {
            Printer.printToConsole("Entered the 'finally' block");
            TimeUnit.SECONDS.sleep(1);
            Printer.printToConsole("""
                                   Now the 'return' statement from the 'finally'
                                       block will be executed. That statement will
                                       cause that an unexpected exception from the
                                       'try' block will be swallowed...""");
            return;
        }
    }

    private void tryFinallyWithoutCatch() throws InterruptedException {
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
                                   Now the program got back to the caller of 'try-finally'
                                       block (without 'catch' block), which received the exception
                                       thrown from that block and will handle it by logging""");
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }
}
