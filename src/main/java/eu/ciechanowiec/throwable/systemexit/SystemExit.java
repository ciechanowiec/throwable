package eu.ciechanowiec.throwable.systemexit;

import eu.ciechanowiec.throwable.Printer;
import org.tinylog.Logger;

import java.util.concurrent.TimeUnit;

/**
 * @author Herman Ciechanowiec
 */
class SystemExit {

    public static void main(String[] args) throws InterruptedException {
        SystemExit showcase = new SystemExit();
        /* Uncomment one of the following lines to see the usage case.
         * Every of those lines stops the program, so don't have
         * uncommented both lines at the same time: */
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
