package org.example.exception;

 public class UnauthorizedActionException extends RuntimeException {

    public UnauthorizedActionException(String message) {
        super(message);
    }

    public UnauthorizedActionException() {
        super("You are not authorized to perform this action");
    }
}
