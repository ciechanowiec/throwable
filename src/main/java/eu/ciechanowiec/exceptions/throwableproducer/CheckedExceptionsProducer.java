package eu.ciechanowiec.exceptions.throwableproducer;

import org.tinylog.Logger;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author Herman Ciechanowiec
 * This class shows the usage of checked exceptions
 */
class CheckedExceptionsProducer {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        /* Do not change the order of the execution
         * of the methods listed below. It can spoil the result */
        CheckedExceptionsProducer producer = new CheckedExceptionsProducer();
        producer.classNotFound();
        producer.io();
        producer.parse();
        producer.interrupted();
    }

    private void classNotFound() throws InterruptedException {
        separateUsageCase("ClassNotFoundException");
        try {
            printToConsole("Creating an existing class...");
            String existingClassName = "java.lang.String";
            Class.forName(existingClassName);
            printToConsole("Creating a non-existing class...");
            String notExistingClassName = "java.lang.AAAAAAAAAAAAA";
            Class.forName(notExistingClassName);
        } catch (ClassNotFoundException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }

    private void io() throws FileNotFoundException, InterruptedException {
        separateUsageCase("IOException");
        String path = "src/main/resources/sample.txt";
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        try {
            printToConsole("Reading and printing the first line from a file...");
            String firstLine = bufferedReader.readLine();
            printToConsole(firstLine);
            printToConsole("Closing the reader...");
            bufferedReader.close();
            printToConsole("Reading and printing the next line from a file...");
            String secondLine = bufferedReader.readLine();
            printToConsole(secondLine);
        } catch (IOException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }

    private void parse() throws InterruptedException {
        separateUsageCase("ParseException");
        try {
            String datePattern = "yyyy-MM-dd";
            String validDataRepresentation = "2022-03-03";
            String invalidDataRepresentation = "aaaaaaaaaaaaaaaaaaaaaaaaa";
            DateFormat dateFormat = new SimpleDateFormat(datePattern);
            printToConsole("Parsing valid date representation...");
            dateFormat.parse(validDataRepresentation);
            printToConsole("Parsing invalid date representation...");
            dateFormat.parse(invalidDataRepresentation);
        } catch (ParseException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
    }
    private void interrupted() throws InterruptedException {
        separateUsageCase("InterruptedException");
        SleepingThread sleepingThread = new SleepingThread();
        printToConsole("Starting a thread...");
        sleepingThread.start();
        TimeUnit.SECONDS.sleep(2); // To let the thread start before it will be interrupted
        printToConsole("Interrupting the sleeping thread...");
        sleepingThread.interrupt();
    }

    private class SleepingThread extends Thread {
        @Override
        public void run() {
            try {
                printToConsole("Putting the thread to sleep...");
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException exception) {
                Logger.error(exception);
            }
        }
    }

    private void printToConsole(String message) {
        System.out.println(message);
    }

    private void separateUsageCase(String exceptionName) {
        printToConsole(String.format("""
                                    \n======================
                                    %s usage case
                                    ======================""", exceptionName));
    }
}
