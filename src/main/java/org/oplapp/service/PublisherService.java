package org.oplapp.service;

import org.oplapp.exceptions.*;
import org.oplapp.model.*;
import org.oplapp.repository.*;
import org.oplapp.validator.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * This class provides publisher related service methods.
 */
@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final ValidationHandler<Publisher> validator;

    public PublisherService(final PublisherRepository publisherRepository, final ValidationHandler<Publisher> validator) {
        this.publisherRepository = publisherRepository;
        this.validator = validator;
    }


    /**
     * Calls the CrudRepository's findById method to query a publisher.
     *
     * @param publisherId the given publisher id
     * @return a Publisher instance
     */
    public Publisher getPublisherById(final Long publisherId) {
        return publisherRepository.findById(publisherId)
                .orElseThrow(()-> new PublisherNotFoundException(publisherId));
    }


    /**
     * Calls the CrudRepository's findAll method to query all publishers in the database.
     *
     * @param page the given page number
     * @param size the given page size
     * @return a Map of String and Object, containing a list of Publishers instances and pagination information
     */
    public Map<String, Object> getAllPublishers(final int page, final int size) {
        final int oneBasedPage = page == 0 ? page : page - 1;
        final Pageable initialPage = PageRequest.of(oneBasedPage, size, Sort.by("publisherName"));
        final Page<Publisher> publisherPage = publisherRepository.findAll(initialPage);

        final List<Publisher> allPublishers = publisherPage.getContent();

        final Map<String, Object> response = new HashMap<>();
        response.put("publishers", allPublishers);
        response.put("totalElements", publisherPage.getTotalElements());
        response.put("totalPages", publisherPage.getTotalPages());
        response.put("currentPage", publisherPage.getNumber() + 1);
        response.put("pageSize", publisherPage.getSize());

        return response;
    }


    /**
     * Calls the AuthorRepository's queryAuthors method to query all publishers matching the given filter criteria.
     *
     * @param publisherName the given publisher name
     * @param page the given page number
     * @param size the given page size
     * @return a Map of String and Object, containing a list of Publisher instances and pagination information
     */
    public Map<String, Object> filterPublishers(final String publisherName, final int page, final int size) {
        final int oneBasedPage = page == 0 ? page : page - 1;
        final Pageable initialPage = PageRequest.of(oneBasedPage, size, Sort.by("publisherName"));
        final Page<Publisher> publisherPage = publisherRepository.queryPublishers(publisherName, initialPage);

        final List<Publisher> allPublishers = publisherPage.getContent();

        final Map<String, Object> response = new HashMap<>();
        response.put("publishers", allPublishers);
        response.put("totalElements", publisherPage.getTotalElements());
        response.put("totalPages", publisherPage.getTotalPages());
        response.put("currentPage", publisherPage.getNumber() + 1);
        response.put("pageSize", publisherPage.getSize());

        return response;
    }


    /**
     * Calls the CrudRepository's save method to persist the given Publisher instance.
     * @param publisher the given Publisher instance
     * @return a Publisher instance
     */
    public Publisher saveOrUpdatePublisher(final Publisher publisher) {
        // Checks whether the given instance is a valid object and
        // throws an exception if not so
        validator.handleValidation(publisher);

        return publisherRepository.save(publisher);
    }


    /**
     * Calls the CrudRepository's deleteById method to delete a publisher from the database by the given id.
     * @param publisherId the given publisher id
     */
    public void deletePublisherById(final Long publisherId) {
        if (!publisherRepository.existsById(publisherId)) {
            throw new PublisherNotFoundException(publisherId);
        } else {
            publisherRepository.deleteById(publisherId);
        }
    }
}
