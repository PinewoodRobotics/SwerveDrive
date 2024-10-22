package org.pwrup.error;

public class PidSetError extends Exception {
    public PidSetError() {
        super("Error while setting PID!");
    }

    public PidSetError(String message) {
        super(message);
    }
}
