package eu.ciechanowiec.throwable.multicatch;

import eu.ciechanowiec.throwable.Printer;
import org.tinylog.Logger;

import java.util.concurrent.TimeUnit;

/**
 * @author Herman Ciechanowiec
 */
class Multicatch {

    public static void main(String[] args) throws InterruptedException {
        Multicatch multicatch = new Multicatch();
        multicatch.multicatchInMultipleBlock();
        multicatch.multicatchInSingleBlock();
    }

    private void multicatchInMultipleBlock() throws InterruptedException {
        Printer.separateUsageCase("""
                                  Multicatching with
                                  multiple 'catch' blocks""");
        try {
            Printer.printToConsole("Entered the 'try' block");
            Printer.printToConsole("Throwing a new exception...");
            throw new RuntimeException("Broader exception");
        } catch (IndexOutOfBoundsException exception) {
            TimeUnit.SECONDS.sleep(2);
            Printer.printToConsole("""
                                   Entered the first 'catch' block.
                                       It means that a narrower exception was caught""");
            Logger.error(exception);
        } catch (RuntimeException exception) {
            TimeUnit.SECONDS.sleep(2);
            Printer.printToConsole("""
                                   Entered the second 'catch' block.
                                       It means that a broader exception was caught""");
            Logger.error(exception);
        }
    }

    private void multicatchInSingleBlock() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        Printer.separateUsageCase("""
                                  Multicatching with a
                                  single 'catch' block""");
        try {
            Printer.printToConsole("Entered the 'try' block");
            Class.forName("java.lang.String");
            Printer.printToConsole("Throwing a new exception...");
            throw new RuntimeException("Sample exception");
        } catch (RuntimeException | ClassNotFoundException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }
}
