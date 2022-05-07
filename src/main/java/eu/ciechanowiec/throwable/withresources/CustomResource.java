package eu.ciechanowiec.throwable.withresources;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Herman Ciechanowiec
 */
class CustomResource implements Closeable {

    private boolean isClosed;

    CustomResource() {
        isClosed = false;
    }

    String readLine() {
        if (isClosed) {
            throw new RuntimeException("THe resource is closed. Reading is illegal");
        }
        return produceRandomString();
    }

    @Override
    public void close() throws IOException {
        isClosed = true;
    }

    boolean isClosed() {
        return isClosed;
    }

    private String produceRandomString() {
        return IntStream.rangeClosed(0, 5)
                        .boxed()
                        .map(number -> ThreadLocalRandom.current().nextInt(1000))
                        .map(String::valueOf)
                        .collect(Collectors.joining());
    }
}
