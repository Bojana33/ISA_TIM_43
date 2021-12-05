package isa2.demo.Exception;

public class EmailNotExistsException extends RuntimeException {
    public EmailNotExistsException(String message) {
        super(message);
    }
}
