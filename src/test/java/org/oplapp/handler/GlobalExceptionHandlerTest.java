package org.oplapp.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oplapp.exceptions.*;
import org.oplapp.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;


    @Test
    void handleResourceNotFoundException() {
        // Arrange
        final Author author = new Author(1L, "Andrea", "Meyer");
        final AuthorNotFoundException exception = new AuthorNotFoundException(author.getAuthorId());

        // Act
        final ResponseEntity<String> responseEntity = globalExceptionHandler.handleResourceNotFoundException(exception);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Author with id " + author.getAuthorId() + " not found", responseEntity.getBody());
    }


    @Test
    void handleIllegalArgumentException() {
        // Arrange
        final IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");

        // Act
        final ResponseEntity<String> responseEntity = globalExceptionHandler.handleIllegalArgumentException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid argument", responseEntity.getBody());
    }


    @Test
    void handleConstraintViolationException() {
        // Arrange
        final InvalidInstanceException exception = new InvalidInstanceException(Set.of("Error 1", "Error 2"));

        // Act
        final ResponseEntity<Set<String>> responseEntity = globalExceptionHandler.handleConstraintViolationException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(Set.of("Error 1", "Error 2"), responseEntity.getBody());
    }


    @Test
    void handleSQLException() {
        // Act
        final ResponseEntity<String> responseEntity = globalExceptionHandler.handleSQLException();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("An SQL Error occurred", responseEntity.getBody());
    }


    @Test
    void handleConnectionException() {
        // Act
        final ResponseEntity<String> responseEntity = globalExceptionHandler.handleConnectionException();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Connection Error", responseEntity.getBody());
    }
}
