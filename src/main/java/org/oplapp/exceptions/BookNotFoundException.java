package org.oplapp.exceptions;

import jakarta.persistence.*;

/**
 * This exception gets thrown when a requested book can not be found in the database.
 */
public class BookNotFoundException extends EntityNotFoundException {
    public BookNotFoundException(final Long bookId) {
        super("Book with id " + bookId + " not found");
    }
}
