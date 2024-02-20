package org.oplapp.handler;

import jakarta.persistence.*;
import org.oplapp.exceptions.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.*;
import java.sql.*;
import java.util.*;

/**
 * An instance of this class serves as a global exception handler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles exceptions related to resources which could not be found in the database.
     * @param exception the given exception
     * @return a ResponseEntity of type String
     */
    @ExceptionHandler({BookNotFoundException.class, AuthorNotFoundException.class, PublisherNotFoundException.class,
                       TagNotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<String> handleResourceNotFoundException(final EntityNotFoundException exception) {
        logger.error("Resource not found exception: {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }


    /**
     * Handles IllegalArgumentExceptions.
     * @param exception the given exception
     * @return a ResponseEntity of type String
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(final IllegalArgumentException exception) {
        logger.error("Illegal argument exception: {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


    /**
     * Handles exceptions related to validation constraints.
     * @param exception the given exception
     * @return a ResponseEntity of type Set of String
     */
    @ExceptionHandler(InvalidInstanceException.class)
    public ResponseEntity<Set<String>> handleConstraintViolationException(final InvalidInstanceException exception) {
        logger.error("Constraint violation exception: {}", exception.getErrorMessages());
        return new ResponseEntity<>(exception.getErrorMessages(), HttpStatus.BAD_REQUEST);
    }


    /**
     * Handles SQL related exceptions.
     * @return a ResponseEntity of type String
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException() {
        logger.error("SQL exception occurred");
        return new ResponseEntity<>("An SQL Error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Handles exceptions related to connectivity issues.
     * @return a ResponseEntity of type String
     */
    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<String> handleConnectionException() {
        logger.error("Connection exception occurred");
        return new ResponseEntity<>("Connection Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
