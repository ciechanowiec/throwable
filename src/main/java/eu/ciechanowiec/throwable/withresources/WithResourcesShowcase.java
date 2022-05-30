package eu.ciechanowiec.throwable.withresources;

import eu.ciechanowiec.throwable.Printer;
import org.tinylog.Logger;

import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Herman Ciechanowiec
 */
class WithResourcesShowcase {

    public static void main(String[] args) throws IOException, InterruptedException {
        WithResourcesShowcase showcase = new WithResourcesShowcase();

        showcase.tryWithResourcess_ExceptionDuringClosing();
        showcase.tryWithoutResources();
        showcase.tryWithResources_OneResource_ExpectedException();
        showcase.tryWithResources_OneResource_UnexpectedException();
        showcase.tryWithResources_TwoResources_ExpectedException();
        showcase.tryWithResources_CustomResource_ExpectedException();
        showcase.tryWithResources_ExceptionDuringClosing();
    }

    private void tryWithResourcess_ExceptionDuringClosing()
            throws InterruptedException, IOException {
        TimeUnit.SECONDS.sleep(1);
        Printer.separateUsageCase("""
                                  'try-with-resources'
                                  [unexpected exception
                                  when closing]""");

        /* 'close()' method of FlawedCustomResource looks like this:
        @Override
        public void close() throws IOException {
            throw new CloseException("Exception during resource closing occurred");
        }*/

        CustomResource customResourceOne = new CustomResource();
        FlawedCustomResource flawedCustomResource = new FlawedCustomResource();
        CustomResource customResourceTwo = new CustomResource();

        try (customResourceOne;
             flawedCustomResource;
             customResourceTwo) {
            customResourceOne.readLine();
            flawedCustomResource.readLine();
            customResourceTwo.readLine();
            throw new ArithmeticException("I'm an exception from the 'try' block");
        } catch (IndexOutOfBoundsException exception) {
            Logger.error(exception);
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Hi, my friend!");
    }

    private void tryWithoutResources()
            throws FileNotFoundException,
                   InterruptedException {
        Printer.separateUsageCase("""
                                  'try-without-resources'
                                  [expected exception
                                  with one resource]""");
        BufferedReader bufferedReader = sampleReader();
        Printer.printToConsole("Resource created. It will not be closed when exception is thrown");
        try {
            Printer.printToConsole("Reading and printing the first line from a file...");
            String firstLine = bufferedReader.readLine();
            Printer.printToConsole(firstLine);
            Printer.printToConsole("Throwing an expected exception...");
            throw new IOException("Expected exception");
        } catch (IOException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
        Printer.printToConsole("The resource is closed: " + isClosed(bufferedReader));
    }

    private void tryWithResources_OneResource_ExpectedException()
            throws FileNotFoundException,
                   InterruptedException {
        Printer.separateUsageCase("""
                                  'try-with-resources'
                                  [expected exception
                                  with one resource]""");
        BufferedReader bufferedReader = sampleReader();
        Printer.printToConsole("Resource created. It will be closed when exception is thrown");
        try (bufferedReader) {
            Printer.printToConsole("Reading and printing the first line from a file...");
            String firstLine = bufferedReader.readLine();
            Printer.printToConsole(firstLine);
            Printer.printToConsole("Throwing an expected exception...");
            throw new IOException("Expected exception");
        } catch (IOException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
        Printer.printToConsole("The resource is closed: " + isClosed(bufferedReader));
    }

    private void tryWithResources_OneResource_UnexpectedException()
            throws FileNotFoundException,
                   InterruptedException {
        Printer.separateUsageCase("""
                                  'try-with-resources'
                                  [unexpected exception
                                  with one resource]""");
        BufferedReader bufferedReader = sampleReader();
        Printer.printToConsole("Resource created. It will be closed when exception is thrown");
        try {
            try (bufferedReader) {
                Printer.printToConsole("Reading and printing the first line from a file...");
                String firstLine = bufferedReader.readLine();
                Printer.printToConsole(firstLine);
                Printer.printToConsole("Throwing an unexpected exception...");
                throw new ArithmeticException("Unexpected exception");
            } catch (IOException exception) {
                TimeUnit.SECONDS.sleep(1);
                Logger.error(exception);
            }
        } catch (ArithmeticException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
        Printer.printToConsole("The resource is closed: " + isClosed(bufferedReader));
    }

    private void tryWithResources_TwoResources_ExpectedException()
            throws IOException,
                   InterruptedException {
        Printer.separateUsageCase("""
                                  'try-with-resources'
                                  [expected exception
                                  with two resources]""");
        BufferedReader bufferedReader = sampleReader();
        BufferedWriter bufferedWriter = sampleWriter();
        Printer.printToConsole("""
                               Two resources created: reader and writer.
                                   They will be closed when exception is thrown""");
        try (bufferedReader;
             bufferedWriter) {
            Printer.printToConsole("""
                                   Reading the first line from a file
                                       and writing it to another file...""");
            String firstLine = bufferedReader.readLine();
            bufferedWriter.write(firstLine);
            Printer.printToConsole("Throwing an expected exception...");
            throw new IOException("Expected exception");
        } catch (IOException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
        Printer.printToConsole("The reader resource is closed: " + isClosed(bufferedReader));
        Printer.printToConsole("The writer resource is closed: " + isClosed(bufferedWriter));
    }

    private void tryWithResources_CustomResource_ExpectedException()
            throws IOException,
                   InterruptedException {
        Printer.separateUsageCase("""
                                  'try-with-resources'
                                  [expected exception
                                  with a custom resource]""");
        CustomResource customResource = new CustomResource();
        Printer.printToConsole("Custom resource created. It will be closed when exception is thrown");
        try (customResource) {
            Printer.printToConsole("Reading and printing the first line from a resource...");
            String firstLine = customResource.readLine();
            Printer.printToConsole(firstLine);
            Printer.printToConsole("Throwing an expected exception...");
            throw new IOException("Expected exception");
        } catch (IOException exception) {
            TimeUnit.SECONDS.sleep(1);
            Logger.error(exception);
        }
        Printer.printToConsole("The resource is closed: " + customResource.isClosed());
    }

    private void tryWithResources_ExceptionDuringClosing()
            throws InterruptedException, IOException {
        TimeUnit.SECONDS.sleep(1);
        Printer.separateUsageCase("""
                                  'try-with-resources'
                                  [unexpected exception
                                  when closing]""");
        CustomResource customResourceOne = new CustomResource();
        FlawedCustomResource flawedCustomResource = new FlawedCustomResource();
        CustomResource customResourceTwo = new CustomResource();
        Printer.printToConsole("""
                               Three resources created: the second one will throw an unexpected exception
                                   while closing. The others will in turn be closed correctly. All three
                                   resources will be passed to the try-with-resources block in the order
                                   they were created: unflawed, flawed, unflawed""");

        try (customResourceOne;
             flawedCustomResource;
             customResourceTwo) {
            Printer.printToConsole("""
                    Entered the 'try' block. Now all three
                        resources will perform some action...""");
            customResourceOne.readLine();
            flawedCustomResource.readLine();
            customResourceTwo.readLine();
            Printer.printToConsole("""
                    Throwing an expected exception from the 'try' block (IndexOutOfBoundsException).
                        An exception that will be thrown while closing the second (flawed) resource
                        (CloseException) will be suppressed and automatically added as a suppressed
                        to the expected exception (IndexOutOfBoundsException) that will be thrown now...""");
            throw new IndexOutOfBoundsException("Expected exception from the 'try' block");
        } catch (IndexOutOfBoundsException exception) {
            TimeUnit.SECONDS.sleep(2);
            Printer.printToConsole("""
                    Entered the 'catch' block. Now an expected exception from
                        the 'try' block will be logged (IndexOutOfBoundsException)...""");
            Logger.error(exception);
            TimeUnit.SECONDS.sleep(2);
            Printer.printToConsole("""
                    An expected exception from the 'try' block (IndexOutOfBoundsException)
                        has inside a suppressed exception that was thrown while closing the
                        second (flawed) resource (CloseException). Now that suppressed
                        exception will be extracted and logged...""");
            Logger.error(exception.getSuppressed()[0]);
        }
        TimeUnit.SECONDS.sleep(2);
        Printer.printToConsole("The first (unflawed) resource is closed: " + customResourceOne.isClosed());
        Printer.printToConsole("The second (flawed) resource is closed: " + flawedCustomResource.isClosed());
        Printer.printToConsole("The third (unflawed) resource is closed: " + customResourceTwo.isClosed());
    }

    private boolean isClosed(BufferedReader bufferedReader) {
        try {
            return !bufferedReader.ready();
        } catch (IOException exception) {
            return true;
        }
    }

    private boolean isClosed(BufferedWriter bufferedWriter) {
        try {
            bufferedWriter.flush();
            return false;
        } catch (IOException exception) {
            return true;
        }
    }

    private BufferedReader sampleReader() throws FileNotFoundException {
        String path = "src/main/resources/sample.txt";
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        return new BufferedReader(fileReader);
    }

    private BufferedWriter sampleWriter() throws IOException {
        String path = "src/main/resources/inputs.txt";
        File file = new File(path);
        FileWriter fileWriter = new FileWriter(file);
        return new BufferedWriter(fileWriter);
    }
}
