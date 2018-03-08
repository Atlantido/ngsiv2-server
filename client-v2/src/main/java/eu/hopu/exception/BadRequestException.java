package eu.hopu.exception;

/**
 * 400 Bad request
 */
public class BadRequestException extends Ngsi2Exception {

    private final static String errorMessage = "Bad request: %s";

    public BadRequestException(String message) {
        super("400", String.format(errorMessage, message), null);
    }
}
