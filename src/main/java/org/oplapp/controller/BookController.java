package org.oplapp.controller;

import org.oplapp.model.*;
import org.oplapp.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * Controller class providing book related api endpoints.
 */
@RestController
@RequestMapping(path = "/books")
public class BookController {

    private final BookService bookService;

    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }


    /**
     * Get bookById: returns a book as JSON if found in the database.
     *
     * @param bookId the given book id
     * @return a ResponseEntity of type Book
     */
    @GetMapping(path = "/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable("bookId") final Long bookId) {
        final Book book = bookService.getBookById(bookId);

        return new ResponseEntity<>(book, HttpStatus.OK);
    }


    /**
     * Get books: Returns all books as JSON.
     *
     * @param page the given page number
     * @param size the amount of items that should be present on a page
     * @return a ResponseEntity of type Map of String and Object, containing a list of Book instances and pagination information
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllBooks(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "25") final int size
    ) {
        final Map<String, Object> response = bookService.getAllBooks(page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Get books: Returns the books matching the given filter criteria as JSON.
     *
     * @param bookTitle       the given book title
     * @param authorId        the given author id
     * @param authorFirstname the given author firstname
     * @param authorLastname  the given author lastname
     * @param publishedBefore the given publishing year min
     * @param publishedAfter  the given publishing year max
     * @param isbn            the given isbn
     * @param publisherId     the given publisher id
     * @param publisher       the given publisher name
     * @param tag             the given tag
     * @param shelf           the given shelf
     * @param ledge           the given ledge
     * @param page            the given page number
     * @param size            the amount of items that should be present on a page
     * @return a ResponseEntity of type Map of String and Object, containing a list of Book instances and pagination information
     */
    @GetMapping(path = "/filter")
    public ResponseEntity<Map<String, Object>> filterBooks(
            @RequestParam(name = "bookTitle", required = false) final String bookTitle,
            @RequestParam(name = "authorId", required = false) final Long authorId,
            @RequestParam(name = "authorFName", required = false) final String authorFirstname,
            @RequestParam(name = "authorLName", required = false) final String authorLastname,
            @RequestParam(name = "publishedBefore", required = false) final Integer publishedBefore,
            @RequestParam(name = "publishedAfter", required = false) final Integer publishedAfter,
            @RequestParam(name = "isbn", required = false) final String isbn,
            @RequestParam(name = "publisherId", required = false) final Long publisherId,
            @RequestParam(name = "publisher", required = false) final String publisher,
            @RequestParam(name = "tag", required = false) final String tag,
            @RequestParam(name = "shelf", required = false) final String shelf,
            @RequestParam(name = "ledge", required = false) final String ledge,
            @RequestParam(defaultValue = "1") final int page,
            @RequestParam(defaultValue = "25") final int size
    ) {
        final Map<String, Object> response = bookService.filterBooks(bookTitle, authorId, authorFirstname, authorLastname, publishedBefore, publishedAfter, isbn, publisherId, publisher, tag, shelf, ledge, page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Post books: Inserts a new book or updates an existing one.
     *
     * @param book the given book
     * @return a ResponseEntity of type Book
     */
    @PostMapping
    public ResponseEntity<Book> saveOrUpdateBook(@RequestBody final Book book) {
        final Book processedBook = bookService.saveOrUpdateBook(book);

        return new ResponseEntity<>(processedBook, HttpStatus.CREATED);
    }


    /**
     * Delete book: Deletes the associated book if it's existing in the database.
     *
     * @param bookId the given book id
     * @return a ResponseEntity of type Void
     */
    @DeleteMapping(path = "/{bookId}")
    public ResponseEntity<Void> deleteBookById(@PathVariable("bookId") final Long bookId) {
        bookService.deleteBookById(bookId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
