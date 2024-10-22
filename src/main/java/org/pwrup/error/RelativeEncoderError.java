package org.pwrup.error;

public class RelativeEncoderError extends Exception {
    public RelativeEncoderError() {
        super("Error while getting relative encoder!");
    }

    public RelativeEncoderError(String message) {
        super(message);
    }
}
