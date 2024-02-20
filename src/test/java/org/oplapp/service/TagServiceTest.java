package org.oplapp.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.oplapp.exceptions.*;
import org.oplapp.repository.*;
import org.oplapp.validator.*;
import org.oplapp.model.Tag;

import java.util.*;



@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ValidationHandler<Tag> validationHandler;

    private TagService tagServiceUnderTest;

    @BeforeEach
    void setup() {
        tagServiceUnderTest = new TagService(tagRepository, validationHandler);
    }

    @Test
    void getTagById_ShouldReturnATagIfIdExists() {
        // Arrange
        final Tag testTag = new Tag(1L, "Java");
        final Long tagId = testTag.getTagId();

        when(tagRepository.findById(tagId)).thenReturn(java.util.Optional.of(testTag));

        // Act
        final Tag resultTag = tagServiceUnderTest.getTagById(tagId);

        // Assert
        assertEquals(testTag, resultTag);
    }

    @Test
    void getTagById_ShouldThrowAnExceptionIfIdDoesNotExist() {
        // Arrange
        final Long notExistingTagId = 99999L;

        when(tagRepository.findById(notExistingTagId)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(TagNotFoundException.class, () -> tagServiceUnderTest.getTagById(notExistingTagId));
    }

    @Test
    void getAllTags_ShouldReturnAListOfTags() {
        // Arrange
        final List<Tag> mockTags = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            mockTags.add(new Tag("Tag" + i));
        }

        when(tagRepository.findAll()).thenReturn(mockTags);

        // Act
        final List<Tag> result = tagServiceUnderTest.getAllTags();

        // Assert
        assertEquals(mockTags, result);
    }

    @Test
    void saveOrUpdateTag_ShouldValidateTheGivenTagInstanceAndReturnTheSavedTag() {
        // Arrange
        final Tag inputTag = new Tag("Travel");
        final Tag savedTag = new Tag(1L, "Travel");

        when(tagRepository.save(inputTag)).thenReturn(savedTag);

        // Act
        final Tag resultTag = tagServiceUnderTest.saveTag(inputTag);

        // Assert
        assertEquals(savedTag, resultTag);
        verify(validationHandler, times(1)).handleValidation(inputTag);
        verify(tagRepository, times(1)).save(inputTag);
    }

    @Test
    void deleteTagById_DeletesTagIfIdExists() {
        // Arrange
        final Long tagId = 1L;

        when(tagRepository.existsById(tagId)).thenReturn(true);

        // Act
        tagServiceUnderTest.deleteTagById(tagId);

        // Assert
        verify(tagRepository, times(1)).deleteById(tagId);
    }

    @Test
    void deleteTagById_ThrowsExceptionIfIdDoesNotExist() {
        // Arrange
        final Long notExistingTagId = 99999L;

        when(tagRepository.existsById(notExistingTagId)).thenReturn(false);

        // Act and Assert
        assertThrows(TagNotFoundException.class, () -> tagServiceUnderTest.deleteTagById(notExistingTagId));
        verify(tagRepository, never()).deleteById(notExistingTagId);
    }
}
