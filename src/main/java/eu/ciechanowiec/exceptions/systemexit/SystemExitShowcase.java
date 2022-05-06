package eu.ciechanowiec.exceptions.systemexit;

import eu.ciechanowiec.exceptions.Printer;
import org.tinylog.Logger;

import java.util.concurrent.TimeUnit;

/**
 * @author Herman Ciechanowiec
 */
class SystemExitShowcase {

    public static void main(String[] args) throws InterruptedException {
        SystemExitShowcase showcase = new SystemExitShowcase();
        // Uncomment one of the lines to see the usage case:
//        showcase.exitBeforeException();
//        showcase.exitBeforeExceptionWithFinally();
    }

    private void exitBeforeException() throws InterruptedException {
        Printer.separateUsageCase("System exit before exception is thrown");
        try {
            Printer.printToConsole("Entered 'try' block");
            Printer.printToConsole("Exiting system...");
            System.exit(0);
            Printer.printToConsole("Throwing exception...");
            throw new RuntimeException("Sample exception");
        } catch (RuntimeException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }

    private void exitBeforeExceptionWithFinally() throws InterruptedException {
        Printer.separateUsageCase("System exit before exception\n" +
                                  "is thrown with finally");
        try {
            Printer.printToConsole("Entered 'try' block");
            Printer.printToConsole("Exiting system...");
            System.exit(0);
            Printer.printToConsole("Throwing exception...");
            throw new RuntimeException("Sample exception");
        } catch (RuntimeException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        } finally {
            Printer.printToConsole("Entered 'finally' block");
        }
    }
}
