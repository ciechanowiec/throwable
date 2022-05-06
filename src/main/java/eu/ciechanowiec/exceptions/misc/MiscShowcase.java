package eu.ciechanowiec.exceptions.misc;

import eu.ciechanowiec.exceptions.Printer;
import org.tinylog.Logger;

import java.util.concurrent.TimeUnit;

/**
 * @author Herman Ciechanowiec
 */
class MiscShowcase {

    public static void main(String[] args) throws InterruptedException {
        MiscShowcase miscShowcase = new MiscShowcase();
        miscShowcase.multicatchInMultipleBlock();
        miscShowcase.multicatchInSingleBlock();
    }

    private void multicatchInMultipleBlock() throws InterruptedException {
        Printer.separateUsageCase("Multicatching with multiple 'catch' blocks");
        try {
            Printer.printToConsole("Throwing a new exception...");
            throw new RuntimeException();
        } catch (IndexOutOfBoundsException exception) {
            TimeUnit.SECONDS.sleep(2);
            Printer.printToConsole("A narrower exception was caught");
            Logger.error(exception);
        } catch (RuntimeException exception) {
            TimeUnit.SECONDS.sleep(2);
            Printer.printToConsole("A broader exception was caught");
            Logger.error(exception);
        }
    }

    private void multicatchInSingleBlock() throws InterruptedException {
        Printer.separateUsageCase("Multicatching with single 'catch' block");
        try {
            Class.forName("java.lang.String");
            Printer.printToConsole("Throwing a new exception...");
            throw new RuntimeException();
        } catch (RuntimeException | ClassNotFoundException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }
}
