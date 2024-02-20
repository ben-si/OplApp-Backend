package org.oplapp.service;

import org.oplapp.exceptions.*;
import org.oplapp.model.*;
import org.oplapp.repository.*;
import org.oplapp.validator.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * This class provides author related service methods.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ValidationHandler<Book> validator;

    public BookService(final BookRepository bookRepository, final ValidationHandler<Book> validator) {
        this.bookRepository = bookRepository;
        this.validator = validator;
    }


    /**
     * Calls the CrudRepository's findById method to query a book with the given id.
     *
     * @param bookId the given book id
     * @return a Book instance
     */
    public Book getBookById(final Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }


    /**
     * Calls the CrudRepository's findAll method to query all books in the database.
     *
     * @param page the given page number
     * @param size the given page size
     * @return a Map of String and Object, containing a list of Book instances and pagination information
     */
    public Map<String, Object> getAllBooks(final int page, final int size) {
        final int oneBasedPage = page == 0 ? page : page - 1;
        final Pageable initialPage = PageRequest.of(oneBasedPage, size, Sort.by("bookTitle"));
        final Page<Book> bookPage = bookRepository.findAll(initialPage);

        final List<Book> allBooks = bookPage.getContent();

        final Map<String, Object> response = new HashMap<>();
        response.put("books", allBooks);
        response.put("totalElements", bookPage.getTotalElements());
        response.put("totalPages", bookPage.getTotalPages());
        response.put("currentPage", bookPage.getNumber() + 1);
        response.put("pageSize", bookPage.getSize());

        return response;
    }


    /**
     * Calls the AuthorRepository's queryAuthors method to query all books matching the given filter criteria.
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
     * @return a Map of String and Object, containing a list of Book instances and pagination information
     */
    public Map<String, Object> filterBooks(final String bookTitle, final Long authorId, final String authorFirstname, final String authorLastname, final Integer publishedBefore, final Integer publishedAfter, final String isbn, final Long publisherId, final String publisher, final String tag, final String shelf, final String ledge, final int page, final int size) {
        final int oneBasedPage = page == 0 ? page : page - 1;
        final Pageable initialPage = PageRequest.of(oneBasedPage, size, Sort.by("bookTitle"));
        final Page<Book> bookPage = bookRepository.queryBooks(bookTitle, authorId, authorFirstname, authorLastname,
                publishedBefore, publishedAfter, isbn, publisherId, publisher, tag, shelf, ledge, initialPage);
        final List<Book> filteredBooks = bookPage.getContent();

        final Map<String, Object> response = new HashMap<>();
        response.put("books", filteredBooks);
        response.put("totalElements", bookPage.getTotalElements());
        response.put("totalPages", bookPage.getTotalPages());
        response.put("currentPage", bookPage.getNumber() + 1);
        response.put("pageSize", bookPage.getSize());

        return response;
    }


    /**
     * Calls the CrudRepository's save method to persist the given Book instance.
     *
     * @param book the given Book instance
     * @return a Book instance
     */
    public Book saveOrUpdateBook(final Book book) {
        // Checks whether the given instance is a valid object and
        // throws an exception if not so
        validator.handleValidation(book);

        return bookRepository.save(book);
    }


    /**
     * Calls the CrudRepository's deleteById method to delete a book from the database by the given id.
     *
     * @param bookId the given book id
     */
    public void deleteBookById(final Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException(bookId);
        } else {
            bookRepository.deleteById(bookId);
        }
    }
}



