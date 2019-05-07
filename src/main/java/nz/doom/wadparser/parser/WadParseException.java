package nz.doom.wadparser.parser;

public class WadParseException extends Exception {
    public WadParseException() {
        super();
    }

    public WadParseException(String message) {
        super(message);
    }

    public WadParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public WadParseException(Throwable cause) {
        super(cause);
    }

    protected WadParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
