package org.oplapp.controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.oplapp.model.*;
import org.oplapp.service.*;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublisherControllerTest {

    @Mock
    private PublisherService publisherService;

    @InjectMocks
    private PublisherController publisherControllerUnderTest;
    private List<Publisher> publishers;


    @BeforeEach
    void setUp() {
        publishers = List.of(new Publisher(1L, "Test Publisher"));
    }


    @Test
    void testGetPublisherById() {
        // Arrange
        final Long publisherId = 1L;
        final Publisher mockPublisher = publishers.get(0);

        when(publisherService.getPublisherById(publisherId)).thenReturn(mockPublisher);

        // Act
        final ResponseEntity<Publisher> responseEntity = publisherControllerUnderTest.getPublisherById(publisherId);

        // Assert
        verify(publisherService, times(1)).getPublisherById(publisherId);
        assertSame(mockPublisher, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    void testGetAllPublishers() {
        // Arrange
        final int page = 0;
        final int size = 25;

        final Page<Publisher> publisherPage = new PageImpl<>(publishers, PageRequest.of(page, size),100L);
        final List<Publisher> publishers = publisherPage.getContent();

        final Map<String, Object> mockResponse = new HashMap<>();

        mockResponse.put("publishers", publishers);
        mockResponse.put("totalElements", publisherPage.getTotalElements());
        mockResponse.put("totalPages", publisherPage.getTotalPages());
        mockResponse.put("currentPage", publisherPage.getNumber());
        mockResponse.put("pageSize", publisherPage.getSize());

        when(publisherService.getAllPublishers(page, size)).thenReturn(mockResponse);

        // Act
        final ResponseEntity<Map<String, Object>> responseEntity = publisherControllerUnderTest.getAllPublishers(page, size);

        // Assert
        verify(publisherService, times(1)).getAllPublishers(page, size);
        assertSame(mockResponse, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    void testFilterPublishers() {
        // Arrange
        final int page = 0;
        final int size = 25;

        final Page<Publisher> publisherPage = new PageImpl<>(publishers, PageRequest.of(page, size),100L);
        final List<Publisher> filteredPublishers = publisherPage.getContent();

        final Map<String, Object> mockResponse = new HashMap<>();

        mockResponse.put("publishers", filteredPublishers);
        mockResponse.put("totalElements", publisherPage.getTotalElements());
        mockResponse.put("totalPages", publisherPage.getTotalPages());
        mockResponse.put("currentPage", publisherPage.getNumber());
        mockResponse.put("pageSize", publisherPage.getSize());

        when(publisherService.filterPublishers(publishers.get(0).getPublisherName(), page, size)).thenReturn(mockResponse);

        // Act
        final ResponseEntity<Map<String, Object>> responseEntity = publisherControllerUnderTest.filterPublishers(publishers.get(0).getPublisherName(),  page, size);

        // Assert
        verify(publisherService, times(1)).filterPublishers(publishers.get(0).getPublisherName(), page, size);
        assertSame(mockResponse, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    void testSaveOrUpdatePublisher() {
        // Arrange
        final Publisher mockPublisher = publishers.get(0);

        when(publisherService.saveOrUpdatePublisher(mockPublisher)).thenReturn(mockPublisher);

        // Act
        final ResponseEntity<Publisher> responseEntity = publisherControllerUnderTest.saveOrUpdatePublisher(mockPublisher);

        // Assert
        verify(publisherService, times(1)).saveOrUpdatePublisher(mockPublisher);
        assertSame(mockPublisher, responseEntity.getBody());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

final
    @Test
    void testDeletePublisherById() {
        // Arrange
        final Long publisherId = 1L;

        // Act
        final ResponseEntity<Void> responseEntity = publisherControllerUnderTest.deletePublisherById(publisherId);

        // Assert
        verify(publisherService, times(1)).deletePublisherById(publisherId);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}