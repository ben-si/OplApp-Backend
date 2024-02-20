package org.oplapp.controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oplapp.model.Tag;
import org.oplapp.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagControllerTest {

    @Mock
    private TagService tagService;

    @InjectMocks
    private TagController tagControllerUnderTest;
    private List<Tag> tags;


    @BeforeEach
    void setUp() {
        tags = List.of(new Tag(1L, "Test Tag"));
    }


    @Test
    void testGetTagById() {
        // Arrange
        final Long tagId = 1L;
        final Tag mockTag = tags.get(0);

        when(tagService.getTagById(tagId)).thenReturn(mockTag);

        // Act
        final ResponseEntity<Tag> responseEntity = tagControllerUnderTest.getTagById(tagId);

        // Assert
        verify(tagService, times(1)).getTagById(tagId);
        assertSame(mockTag, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    void testGetAllTags() {
        // Arrange
        when(tagService.getAllTags()).thenReturn(tags);

        // Act
        final ResponseEntity<List<Tag>> responseEntity = tagControllerUnderTest.getAllTags();

        // Assert
        verify(tagService, times(1)).getAllTags();
        assertSame(tags, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    void testSaveOrUpdateTag() {
        // Arrange
        final Tag mockTag = tags.get(0);

        when(tagService.saveTag(mockTag)).thenReturn(mockTag);

        // Act
        final ResponseEntity<Tag> responseEntity = tagControllerUnderTest.saveTag(mockTag);

        // Assert
        verify(tagService, times(1)).saveTag(mockTag);
        assertSame(mockTag, responseEntity.getBody());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }


    @Test
    void testDeleteTagById() {
        // Arrange
        final Long tagId = 1L;

        // Act
        final ResponseEntity<Void> responseEntity = tagControllerUnderTest.deleteTagById(tagId);

        // Assert
        verify(tagService, times(1)).deleteTagById(tagId);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}