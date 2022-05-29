package eu.ciechanowiec.throwable.withresources;

/**
 * @author Herman Ciechanowiec
 */
class CloseException extends RuntimeException {
    CloseException(String message) {
        super(message);
    }
}
