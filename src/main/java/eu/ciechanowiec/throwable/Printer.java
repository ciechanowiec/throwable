package eu.ciechanowiec.throwable;

/**
 * @author Herman Ciechanowiec
 */
public class Printer {

    public static void printToConsole(String message) {
        System.out.println(message);
    }

    public static void separateUsageCase(String usageCase) {
        printToConsole(String.format("""
                                    \n======================
                                    %s
                                    ======================""", usageCase));
    }
}
