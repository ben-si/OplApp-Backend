package org.oplapp.exceptions;

import jakarta.persistence.*;

/**
 * This exception gets thrown when a requested publisher can not be found in the database.
 */
public class PublisherNotFoundException extends EntityNotFoundException {
    public PublisherNotFoundException(final Long publisherId) {
        super("Publisher with id " + publisherId + " not found");
    }
}
