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
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final ValidationHandler<Author> validator;


    public AuthorService(final AuthorRepository authorRepository, final ValidationHandler<Author> validator) {
        this.authorRepository = authorRepository;
        this.validator = validator;
    }


    /**
     * Calls the CrudRepository's findById method to query an author.
     *
     * @param authorId the given author id
     * @return an Author instance
     */
    public Author getAuthorById(final Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));

    }


    /**
     * Calls the CrudRepository's findAll method to query all authors in the database.
     *
     * @param page the given page number
     * @param size the given page size
     * @return a Map of String and Object, containing a list of Author instances and pagination information
     */
    public Map<String, Object> getAllAuthors(final int page, final int size) {
        final int oneBasedPage = page == 0 ? page : page - 1;
        final Pageable initialPage = PageRequest.of(oneBasedPage, size, Sort.by("lastname"));
        final Page<Author> authorPage = authorRepository.findAll(initialPage);
        final List<Author> allAuthors = authorPage.getContent();

        final Map<String, Object> response = new HashMap<>();
        response.put("authors", allAuthors);
        response.put("totalElements", authorPage.getTotalElements());
        response.put("totalPages", authorPage.getTotalPages());
        response.put("currentPage", authorPage.getNumber() + 1);
        response.put("pageSize", authorPage.getSize());

        return response;
    }


    /**
     * Calls the AuthorRepository's queryAuthors method to query all authors matching the given filter criteria.
     *
     * @param authorFirstname the given firstname
     * @param authorLastname the given lastname
     * @param page the given page number
     * @param size the given page size
     * @return a Map of String and Object, containing a list of Author instances and pagination information
     */
    public Map<String, Object> filterAuthors(final String authorFirstname, final String authorLastname, final int page, final int size) {
        final int oneBasedPage = page == 0 ? page : page - 1;
        final Pageable initialPage = PageRequest.of(oneBasedPage, size, Sort.by("lastname"));
        final Page<Author> authorPage = authorRepository.queryAuthors(authorFirstname, authorLastname, initialPage);
        final List<Author> filteredAuthors = authorPage.getContent();

        final Map<String, Object> response = new HashMap<>();
        response.put("authors", filteredAuthors);
        response.put("totalElements", authorPage.getTotalElements());
        response.put("totalPages", authorPage.getTotalPages());
        response.put("currentPage", authorPage.getNumber() + 1);
        response.put("pageSize", authorPage.getSize());

        return response;
    }


    /**
     * Calls the CrudRepository's save method to persist the given Author instance.
     * @param author the given Author instance
     * @return an Author instance
     */
    public Author saveOrUpdateAuthor(final Author author) {
        // Checks whether the given instance is a valid object and
        // throws an exception if not so
        validator.handleValidation(author);

        return authorRepository.save(author);
    }


    /**
     * Calls the CrudRepository's deleteById method to delete an author from the database by the given id.
     * @param authorId the given author id
     */
    public void deleteAuthorById(final Long authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new AuthorNotFoundException(authorId);
        } else {
            authorRepository.deleteById(authorId);
        }
    }
}
