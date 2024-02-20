package org.oplapp.exceptions;

import jakarta.persistence.*;

/**
 * This exception gets thrown when a requested tag can not be found in the database.
 */
public class TagNotFoundException extends EntityNotFoundException {
    public TagNotFoundException(final Long tagId) {
        super("Tag with id " + tagId + " not found");
    }
}
