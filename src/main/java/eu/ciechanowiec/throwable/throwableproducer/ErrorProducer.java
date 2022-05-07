package eu.ciechanowiec.throwable.throwableproducer;

import eu.ciechanowiec.throwable.Printer;
import org.tinylog.Logger;

import java.util.concurrent.TimeUnit;

/**
 * @author Herman Ciechanowiec
 * This class shows the usage of StackOverFlowError
 */
class ErrorProducer {

    public static void main(String[] args) throws InterruptedException {
        ErrorProducer producer = new ErrorProducer();
        producer.stackOverFlow();
    }

    private void stackOverFlow() throws InterruptedException {
        try {
            Printer.separateUsageCase("StackOverFlowError");
            Printer.printToConsole("Pushing infinite frames on stack...");
            infiniteRecursion();
        } catch (StackOverflowError stackOverflowError) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(stackOverflowError);
        }
    }

    private void infiniteRecursion() {
        infiniteRecursion();
    }
}
