package org.oplapp.service;

import org.oplapp.exceptions.*;
import org.oplapp.model.*;
import org.oplapp.repository.*;
import org.oplapp.validator.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * This class provides tag related service methods.
 */
@Service
public class TagService {

    private final TagRepository tagRepository;
    private final ValidationHandler<Tag> validator;

    public TagService(final TagRepository tagRepository, final ValidationHandler<Tag> validator) {
        this.tagRepository = tagRepository;
        this.validator = validator;
    }


    /**
     * Calls the CrudRepository's findById method to query a tag.
     *
     * @param tagId the given tag id
     * @return an Tag instance
     */
    public Tag getTagById(final Long tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new TagNotFoundException(tagId));
    }


    /**
     * Calls the CrudRepository's findAll method to query all authors in the database.
     *
     * @return a list of Tag instances
     */
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }


    /**
     * Calls the CrudRepository's save method to persist the given Tag instance.
     *
     * @param tag the given Tag instance
     * @return a Tag instance
     */
    public Tag saveTag(final Tag tag) {
        // Checks whether the given instance is a valid object and
        // throws an exception if not so
        validator.handleValidation(tag);

        return tagRepository.save(tag);
    }


    /**
     * Calls the CrudRepository's deleteById method to delete a tag from the database by the given id.
     *
     * @param tagId the given tag id
     */
    public void deleteTagById(final Long tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new TagNotFoundException(tagId);
        } else {
            tagRepository.deleteById(tagId);
        }
    }
}
