package org.pwrup.error;

public class SetMaxCurrentError extends Exception {

    public SetMaxCurrentError() {
        super("Error while setting max current!");
    }

    public SetMaxCurrentError(String message) {
        super(message);
    }
}
