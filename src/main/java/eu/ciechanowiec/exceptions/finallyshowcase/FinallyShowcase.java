package eu.ciechanowiec.exceptions.finallyshowcase;

import eu.ciechanowiec.exceptions.Printer;
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
    }

    private void finallyWithExpectedException() throws InterruptedException {
        Printer.separateUsageCase("'finally' usage with an\nexpected exception");
        try {
            Printer.printToConsole("Entered 'try' block");
            Printer.printToConsole("Now an expected exception will be thrown...");
            throw new RuntimeException("Expected exception");
        } catch (RuntimeException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        } finally {
            TimeUnit.SECONDS.sleep(2);
            Printer.printToConsole("Entered 'finally' block");
        }
    }

    private void finallyWithUnexpectedException() throws InterruptedException {
        Printer.separateUsageCase("'finally' usage with an\nunexpected exception");
        try {
            Printer.printToConsole("Entered 'try' block");
            Printer.printToConsole("Now an unexpected exception will be thrown...");
            /* This exception will crash the program,
             * but 'finally' block will be executed anyway: */
            throw new IndexOutOfBoundsException("Unexpected exception");
        } catch (ArithmeticException exception) {
            // This block will not be executed
            TimeUnit.SECONDS.sleep(1);
            Printer.printToConsole("Expected exception was caught");
            Logger.error(exception);
        } finally {
            TimeUnit.SECONDS.sleep(2);
            Printer.printToConsole("Entered 'finally' block");
            Printer.printToConsole("""
                                   Now the program will crash because inside the 'try' block
                                   an unexpected exception was thrown. However, before crashing,
                                   the 'finally' block has been executed...""");
        }
        Printer.printToConsole("""
                               This line will not be printed, because it
                               is outside the 'finally' block and the program
                               has crushed.""");
    }
}
