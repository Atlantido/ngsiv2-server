package eu.hopu.exception;

/**
 * 406 Not Acceptable
 */
public class NotAcceptableException extends Ngsi2Exception {

    private final static String message = "Not Acceptable: Accepted MIME types: text/plain.";

    public NotAcceptableException() {
        super("406", message, null);
    }

}
