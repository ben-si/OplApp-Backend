package org.oplapp.controller;

import org.oplapp.model.*;
import org.oplapp.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller class providing tag related api endpoints.
 */
@RestController
@RequestMapping(path = "/tags")
public class TagController {

    private final TagService tagService;


    public TagController(final TagService tagService) {
        this.tagService = tagService;
    }


    /**
     * Get tagById: returns a tag as JSON if it's existing in the database.
     * @param tagId the given tag id
     * @return a ResponseEntity of type Tag
     */
    @GetMapping(path = "/{tagId}")
    public ResponseEntity<Tag> getTagById(@PathVariable("tagId") final Long tagId) {
        final Tag tag = tagService.getTagById(tagId);

        return new ResponseEntity<>(tag, HttpStatus.OK);
    }


    /**
     * Get tags: returns all tags as JSON.
     * @return a ResponseEntity of type List of Tag
     */
    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        final List<Tag> allTags = tagService.getAllTags();

        return new ResponseEntity<>(allTags, HttpStatus.OK);
    }


    /**
     * Post tag: inserts a new author or updates an existing one
     * @param tag the given tag
     * @return a ResponseEntity of type Tag
     */
    @PostMapping
    public ResponseEntity<Tag> saveTag(@RequestBody final Tag tag) {
        final Tag createdTag = tagService.saveTag(tag);

        return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
    }


    /**
     * Delete tag: Deletes the associated tag if it's existing in the database.
     * @param tagId the given tag id
     * @return a ResponseEntity of type Void
     */
    @DeleteMapping(path = "/{tagId}")
    public ResponseEntity<Void> deleteTagById(@PathVariable("tagId") final Long tagId) {
        tagService.deleteTagById(tagId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}