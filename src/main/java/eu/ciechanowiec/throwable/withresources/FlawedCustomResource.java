package eu.ciechanowiec.throwable.withresources;

import java.io.IOException;

/**
 * @author Herman Ciechanowiec
 */
class FlawedCustomResource extends CustomResource {

    FlawedCustomResource(String fileName) {
        super(fileName);
    }

    FlawedCustomResource() {
    }

    @Override
    public void close() throws IOException {
        throw new CloseException("Exception during resource closing occurred");
    }
}
