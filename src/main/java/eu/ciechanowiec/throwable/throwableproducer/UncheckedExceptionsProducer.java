package eu.ciechanowiec.throwable.throwableproducer;

import eu.ciechanowiec.throwable.Printer;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Herman Ciechanowiec
 * This class shows the usage of unchecked exceptions
 */
class UncheckedExceptionsProducer {

    public static void main(String[] args) throws InterruptedException {
        UncheckedExceptionsProducer producer = new UncheckedExceptionsProducer();
        producer.arithmetic();
        producer.nullPointer();
        producer.indexOutOfBounds();
        producer.illegalArgument();
        producer.illegalState();
        producer.classCast();
    }

    private void arithmetic() throws InterruptedException {
        try {
            Printer.separateUsageCase("ArithmeticException");
            Printer.printToConsole("Performing valid division...");
            int validResult = 10 / 2;
            Printer.printToConsole("Performing invalid division...");
            int inValidResult = 10 / 0;
        } catch (ArithmeticException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }

    private void nullPointer() throws InterruptedException {
        try {
            Printer.separateUsageCase("NullPointerException");
            Printer.printToConsole("Calling the instance method of an existing object...");
            String existingObject = "object";
            existingObject.length();
            Printer.printToConsole("Calling the instance method of a non-existing object...");
            String nonExistingObject = null;
            nonExistingObject.length();
        } catch (NullPointerException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }

    private void indexOutOfBounds() throws InterruptedException {
        try {
            Printer.separateUsageCase("IndexOutOfBoundsException");
            List<String> list = new ArrayList<>(List.of("the only object"));
            Printer.printToConsole("Getting an object at an valid index...");
            list.get(0);
            Printer.printToConsole("Getting an object at an invalid index...");
            list.get(1);
        } catch (IndexOutOfBoundsException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }

    private void illegalArgument() throws InterruptedException {
        try {
            Printer.separateUsageCase("IllegalArgumentException");
            Printer.printToConsole("Passing to a function a legal argument...");
            Thread.sleep(100);
            Printer.printToConsole("Passing to a function an illegal argument...");
            Thread.sleep(-100);
        } catch (IllegalArgumentException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }

    private void illegalState() throws InterruptedException {
        try {
            Printer.separateUsageCase("IllegalStateException");
            List<String> list = new ArrayList<>(List.of("First element", "Second element"));
            Printer.printToConsole("Putting the application into legal state...");
            Iterator<String> legalIterator = list.iterator();
            legalIterator.next();
            legalIterator.remove();
            Printer.printToConsole("Putting the application into illegal state...");
            Iterator<String> illegalIterator = list.iterator();
            illegalIterator.remove();
        } catch (IllegalStateException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }

    private void classCast() throws InterruptedException {
        try {
            Printer.separateUsageCase("ClassCastException");
            Printer.printToConsole("Performing legal class cast...");
            Object object = Integer.valueOf(2);
            Printer.printToConsole("Performing illegal class cast...");
            String string = (String) object;
        } catch (ClassCastException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }
}
