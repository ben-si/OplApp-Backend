package org.oplapp.controller;

import org.oplapp.model.*;
import org.oplapp.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller class providing publisher related api endpoints.
 */
@RestController
@RequestMapping(path = "/publishers")
public class PublisherController {

    private final PublisherService publisherService;


    public PublisherController(final PublisherService publisherService) {
        this.publisherService = publisherService;
    }


    /**
     * Get publisherById: returns a publisher as JSON if found in the database.
     * @param publisherId the given publisher id
     * @return a ResponseEntity of type Publisher
     */
    @GetMapping(path = "/{publisherId}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable("publisherId") final Long publisherId) {
        final Publisher publisher = publisherService.getPublisherById(publisherId);

        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }


    /**
     * Get publishers: returns the filtered publishers as JSON.
     * @return a ResponseEntity of type Map of String and Object, containing a list of Publisher instances and pagination information
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPublishers(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "25") final int size
    ) {
        final Map<String, Object> response = publisherService.getAllPublishers(page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Get publishers: returns the filtered publishers as JSON.
     * @return a ResponseEntity of type Map of String and Object, containing a list of Publisher instances and pagination information
     */
    @GetMapping(path = "/filter")
    public ResponseEntity<Map<String, Object>> filterPublishers(
            @RequestParam(name = "name", required = false) final String publisherName,
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "25") final int size
    ) {
        final Map<String, Object> response = publisherService.filterPublishers(publisherName, page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Post publishers: inserts a new publisher or updates an existing one
     * @param publisher the given publisher
     * @return a ResponseEntity of type Publisher
     */
    @PostMapping
    public ResponseEntity<Publisher> saveOrUpdatePublisher(@RequestBody final Publisher publisher) {
        final Publisher processedPublisher = publisherService.saveOrUpdatePublisher(publisher);

        return new ResponseEntity<>(processedPublisher, HttpStatus.CREATED);
    }


    /**
     * Delete publisher: Deletes the associated publisher if it's existing in the database.
     * @param publisherId the given publisher id
     * @return a ResponseEntity of tpe Void
     */
    @DeleteMapping(path = "/{publisherId}")
    public ResponseEntity<Void> deletePublisherById(@PathVariable("publisherId") final Long publisherId) {
       publisherService.deletePublisherById(publisherId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
