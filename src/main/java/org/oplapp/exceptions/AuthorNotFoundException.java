package org.oplapp.exceptions;

import jakarta.persistence.*;

/**
 * This exception gets thrown when a requested author can not be found in the database.
 */
public class AuthorNotFoundException extends EntityNotFoundException {
    public AuthorNotFoundException(final Long authorId) {
        super("Author with id " + authorId + " not found");
    }
}
