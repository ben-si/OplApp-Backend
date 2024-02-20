package org.oplapp.controller;

import jakarta.transaction.*;
import org.oplapp.repository.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


/**
 * Controller class providing statistics related api endpoints.
 */
@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    public StatisticsController(BookRepository bookRepository, final AuthorRepository authorRepository, final PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }


    /**
     * Get getTotalBooks: returns the total amount of books in the database.
     *
     * @return a ResponseEntity of type Integer which represents the total amount of books
     */
    @Transactional
    @GetMapping(path = "/books-total")
    public ResponseEntity<Integer> getTotalBooks() {
        final Integer totalBooks = bookRepository.getTotalBooksCount();
        return new ResponseEntity<>(totalBooks, HttpStatus.OK);
    }


    /**
     * Get getTotalAuthors: returns the total amount of authors in the database.
     *
     * @return a ResponseEntity of type Integer which represents the total amount of authors
     */
    @Transactional
    @GetMapping(path = "/authors-total")
    public ResponseEntity<Integer> getTotalAuthors() {
        final Integer totalAuthors = authorRepository.getTotalAuthorsCount();
        return new ResponseEntity<>(totalAuthors, HttpStatus.OK);
    }


    /**
     * Get getTotalPublishers: returns the total amount of publishers in the database.
     *
     * @return a ResponseEntity of type Integer which represents the total amount of publishers
     */
    @Transactional
    @GetMapping(path = "/publishers-total")
    public ResponseEntity<Integer> getTotalPublishers() {
        final Integer totalPublishers = publisherRepository.getTotalPublishersCount();
        return new ResponseEntity<>(totalPublishers, HttpStatus.OK);
    }
}
