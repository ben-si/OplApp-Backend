package org.oplapp.controller;

import org.oplapp.model.*;
import org.oplapp.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller class providing author related api endpoints.
 */
@RestController
@RequestMapping(path = "/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(final AuthorService authorService) {
        this.authorService = authorService;
    }


    /**
     * Get authorById: returns an author as JSON if found in the database.
     * @param authorId the given author id
     * @return a ResponseEntity of type Author
     */
    @GetMapping(path = "/{authorId}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("authorId") final Long authorId) {
        final Author author = authorService.getAuthorById(authorId);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }


    /**
     * Get authors: returns all authors as JSON.
     * @param page the given page number
     * @param size the amount of items that should be present on a page
     * @return a ResponseEntity of type Map of String and Object, containing a list of Author instances and pagination information
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllAuthors(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "25") final int size
    ) {
        final Map<String, Object> response = authorService.getAllAuthors(page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Get authors: returns the filtered authors as JSON.
     * @param authorFirstname the given author's firstname
     * @param authorLastname  the given author's lastname
     * @return a ResponseEntity of type Map of String and Object, containing a list of Author instances and pagination information
     */
    @GetMapping(path = "/filter")
    public ResponseEntity<Map<String, Object>> filterAuthors(
            @RequestParam(name = "firstname", required = false) final String authorFirstname,
            @RequestParam(name = "lastname", required = false) final String authorLastname,
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "25") final int size
    ) {
        final Map<String, Object> filteredAuthors = authorService.filterAuthors(authorFirstname, authorLastname, page, size);

        return new ResponseEntity<>(filteredAuthors, HttpStatus.OK);
    }


    /**
     * Post author: inserts a new author or updates an existing one
     * @param author the given author
     * @return a ResponseEntity of type Author
     */
    @PostMapping
    public ResponseEntity<Author> saveOrUpdateAuthor(@RequestBody final Author author) {
        final Author processedAuthor = authorService.saveOrUpdateAuthor(author);

        return new ResponseEntity<>(processedAuthor, HttpStatus.CREATED);
    }


    /**
     * Delete author: Deletes the associated author if it's existing in the database.
     * @param authorId the given author id
     * @return a ResponseEntity of type Void
     */
    @DeleteMapping(path = "/{authorId}")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable("authorId") final Long authorId) {
        authorService.deleteAuthorById(authorId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
