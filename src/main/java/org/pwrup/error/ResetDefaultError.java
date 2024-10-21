package org.pwrup.error;

public class ResetDefaultError extends Exception {
    public ResetDefaultError() {
        super("Error while resetting motors to default!");
    }

    public ResetDefaultError(String message) {
        super(message);
    }
}
