package eu.ciechanowiec.throwable.throwableproducer;

import eu.ciechanowiec.throwable.Printer;
import org.tinylog.Logger;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author Herman Ciechanowiec
 * This class shows the usage case of checked exceptions
 */
class CheckedExceptionsProducer {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        /* Do not change the order of the execution
         * of the methods listed below. It can spoil the result */
        CheckedExceptionsProducer producer = new CheckedExceptionsProducer();
        producer.fileNotFound();
        producer.classNotFound();
        producer.io();
        producer.parse();
        producer.interrupted();
    }

    private void fileNotFound() throws InterruptedException {
        Printer.separateUsageCase("FileNotFoundException");
        String path = "MOCKED FILE NAME";
        File file = new File(path);
        try {
            Printer.printToConsole("""
                    Creating an input stream with not existing file.
                        It fill cause FileNotFoundException be thrown...""");
            FileInputStream stream = new FileInputStream(file);
        } catch (FileNotFoundException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }

    private void classNotFound() throws InterruptedException {
        Printer.separateUsageCase("ClassNotFoundException");
        try {
            Printer.printToConsole("Creating an existing class...");
            String existingClassName = "java.lang.String";
            Class.forName(existingClassName);
            Printer.printToConsole("Creating a non-existing class...");
            String notExistingClassName = "java.lang.AAAAAAAAAAAAA";
            Class.forName(notExistingClassName);
        } catch (ClassNotFoundException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }

    private void io() throws FileNotFoundException, InterruptedException {
        Printer.separateUsageCase("IOException");
        String path = "src/main/resources/sample.txt";
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        try {
            Printer.printToConsole("Reading and printing the first line from a file...");
            String firstLine = bufferedReader.readLine();
            Printer.printToConsole(firstLine);
            Printer.printToConsole("Closing the reader...");
            bufferedReader.close();
            Printer.printToConsole("Reading and printing the next line from a file...");
            String secondLine = bufferedReader.readLine();
            Printer.printToConsole(secondLine);
        } catch (IOException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }

    private void parse() throws InterruptedException {
        Printer.separateUsageCase("ParseException");
        try {
            String datePattern = "yyyy-MM-dd";
            String validDataRepresentation = "2022-03-03";
            String invalidDataRepresentation = "aaaaaaaaaaaaaaaaaaaaaaaaa";
            DateFormat dateFormat = new SimpleDateFormat(datePattern);
            Printer.printToConsole("Parsing valid date representation...");
            dateFormat.parse(validDataRepresentation);
            Printer.printToConsole("Parsing invalid date representation...");
            dateFormat.parse(invalidDataRepresentation);
        } catch (ParseException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }

    private void interrupted() throws InterruptedException {
        Printer.separateUsageCase("InterruptedException");
        SleepingThread sleepingThread = new SleepingThread();
        Printer.printToConsole("Starting a thread...");
        sleepingThread.start();
        TimeUnit.SECONDS.sleep(2); // To let the thread start before it will be interrupted
        Printer.printToConsole("Interrupting the sleeping thread...");
        sleepingThread.interrupt();
    }

    private void numberFormatException() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        Printer.separateUsageCase("NumberFormatException");
        try {
            String validNumberRepresentation = "6";
            String invalidNumberRepresentation = "six";
            Printer.printToConsole("Parsing valid number representation...");
            Integer.parseInt(validNumberRepresentation);
            Printer.printToConsole("Parsing invalid number representation...");
            Integer.parseInt(invalidNumberRepresentation);
        } catch (NumberFormatException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }

    private static class SleepingThread extends Thread {
        @Override
        public void run() {
            try {
                Printer.printToConsole("Putting the thread to sleep...");
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException exception) {
                Logger.error(exception);
            }
        }
    }
}
