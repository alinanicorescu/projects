package biz.netcentric;

import java.io.IOException;

/**
 * Created by alinanicorescu on 09/04/2017.
 * Exception class for all exceptions occurred during Slightly script processing
 */
public final class SlightlyProcessingException extends Exception {

    private static final String WRITE_ERROR_MESSAGE = "An exception occurred while writing to output";

    public SlightlyProcessingException(String message, Exception e) {
        super(message, e);
    }

    public static final SlightlyProcessingException createWriteException(IOException e) {
        return new SlightlyProcessingException(WRITE_ERROR_MESSAGE, e);
    }
}
