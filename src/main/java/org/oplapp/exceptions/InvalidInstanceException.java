package org.oplapp.exceptions;

import jakarta.validation.*;

import java.util.*;

/**
 * This exception gets thrown when an object does not fit the specified criteria.
 */
public class InvalidInstanceException extends ValidationException {

    private final Set<String> errorMessages;


    public InvalidInstanceException(final Set<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Set<String> getErrorMessages() {
        return errorMessages;
    }
}
