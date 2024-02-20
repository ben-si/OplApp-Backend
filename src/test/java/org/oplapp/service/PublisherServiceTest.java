package org.oplapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oplapp.exceptions.*;
import org.oplapp.model.*;
import org.oplapp.repository.*;
import org.oplapp.validator.*;
import org.springframework.data.domain.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class PublisherServiceTest {

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private ValidationHandler<Publisher> validationHandler;

    private PublisherService publisherServiceUnderTest;

    @BeforeEach
    void setup() {
        publisherServiceUnderTest = new PublisherService(publisherRepository, validationHandler);
    }


    @Test
    void getPublisherById_ShouldReturnAPublisherIfIdExists() {
        // Arrange
        final Publisher testPublisher = new Publisher(1L, "Test Publisher");
        final Long publisherId = testPublisher.getPublisherId();

        when(publisherRepository.findById(publisherId)).thenReturn(java.util.Optional.of(testPublisher));

        // Act
        final Publisher resultPublisher = publisherServiceUnderTest.getPublisherById(publisherId);

        // Assert
        assertEquals(testPublisher, resultPublisher);
    }


    @Test
    void getPublisherById_ShouldThrowAnExceptionIfIdDoesNotExist() {
        // Arrange
        final Long notExistingPublisherId = 99999L;

        when(publisherRepository.findById(notExistingPublisherId)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(PublisherNotFoundException.class, () -> publisherServiceUnderTest.getPublisherById(notExistingPublisherId));
    }


    @Test
    void getAllPublishers_ShouldReturnAMapWithPublishersAndPaginationInfo() {
        // Arrange
        final int page = 0;
        final int size = 25;

        final List<Publisher> mockPublishers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            mockPublishers.add(new Publisher("Test Publisher" + i));
        }

        final Page<Publisher> mockPage = new PageImpl<>(mockPublishers, PageRequest.of(page, size), 100L);

        when(publisherRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        // Act
        final Map<String, Object> result = publisherServiceUnderTest.getAllPublishers(page, size);

        // Assert
        assertTrue(result.containsKey("publishers"));
        assertTrue(result.containsKey("totalElements"));
        assertTrue(result.containsKey("totalPages"));
        assertTrue(result.containsKey("currentPage"));
        assertTrue(result.containsKey("pageSize"));

        assertEquals(mockPublishers, result.get("publishers"));
        assertEquals(100L, result.get("totalElements"));
        assertEquals(4, result.get("totalPages"));
        assertEquals(1, result.get("currentPage"));
        assertEquals(25, result.get("pageSize"));
    }


    @Test
    void filterPublishers_ShouldReturnAMapWithFilteredPublishersAndPaginationInfo() {
        // Arrange
        final int page = 0;
        final int size = 25;
        final String publisherName = "Test Publisher";

        final List<Publisher> mockFilteredPublishers = new ArrayList<>();
        mockFilteredPublishers.add(new Publisher(publisherName));
        final Page<Publisher> mockPage = new PageImpl<>(mockFilteredPublishers, PageRequest.of(page, size), 1L);

        when(publisherRepository.queryPublishers(eq(publisherName), any(Pageable.class)))
                .thenReturn(mockPage);

        // Act
        final Map<String, Object> result = publisherServiceUnderTest.filterPublishers(publisherName, page, size);

        // Assert
        assertTrue(result.containsKey("publishers"));
        assertTrue(result.containsKey("totalElements"));
        assertTrue(result.containsKey("totalPages"));
        assertTrue(result.containsKey("currentPage"));
        assertTrue(result.containsKey("pageSize"));

        assertEquals(mockFilteredPublishers, result.get("publishers"));
        assertEquals(1L, result.get("totalElements"));
        assertEquals(1, result.get("totalPages"));
        assertEquals(1, result.get("currentPage"));
        assertEquals(25, result.get("pageSize"));
    }


    @Test
    void saveOrUpdatePublisher_ShouldValidateTheGivenPublisherInstanceAndReturnTheSavedPublisher() {
        // Arrange
        final Publisher inputPublisher = new Publisher(1L, "Test Publisher");
        final Publisher savedPublisher = new Publisher(1L, "Test Publisher");

        when(publisherRepository.save(inputPublisher)).thenReturn(savedPublisher);

        // Act
        final Publisher resultPublisher = publisherServiceUnderTest.saveOrUpdatePublisher(inputPublisher);

        // Assert
        assertEquals(savedPublisher, resultPublisher);
        verify(validationHandler, times(1)).handleValidation(inputPublisher);
        verify(publisherRepository, times(1)).save(inputPublisher);
    }


    @Test
    void deletePublisherById_DeletesPublisherIfIdExists() {
        // Arrange
        final Long publisherId = 1L;

        when(publisherRepository.existsById(publisherId)).thenReturn(true);

        // Act
        publisherServiceUnderTest.deletePublisherById(publisherId);

        // Assert
        verify(publisherRepository, times(1)).deleteById(publisherId);
    }


    @Test
    void deletePublisherById_ThrowsExceptionIfIdDoesNotExist() {
        // Arrange
        final Long notExistingPublisherId = 99999L;

        when(publisherRepository.existsById(notExistingPublisherId)).thenReturn(false);

        // Act and Assert
        assertThrows(PublisherNotFoundException.class, () -> publisherServiceUnderTest.deletePublisherById(notExistingPublisherId));
        verify(publisherRepository, never()).deleteById(notExistingPublisherId);
    }
}
