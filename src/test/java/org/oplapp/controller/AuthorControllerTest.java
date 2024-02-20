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
class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorControllerUnderTest;
    private List<Author> authors;


    @BeforeEach
    void setUp() {
        authors = List.of(new Author(1L, "Andrea", "Meyer"));
    }


    @Test
    void GetAuthorById() {
        // Arrange
        final Long authorId = 1L;
        final Author mockAuthor = authors.get(0);

        when(authorService.getAuthorById(authorId)).thenReturn(mockAuthor);

        // Act
        final ResponseEntity<Author> responseEntity = authorControllerUnderTest.getAuthorById(authorId);

        // Assert
        verify(authorService, times(1)).getAuthorById(authorId);
        assertSame(mockAuthor, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    void GetAllAuthors() {
        // Arrange
        final int page = 0;
        final int size = 25;

        final Page<Author> authorPage = new PageImpl<>(authors, PageRequest.of(page, size),100L);
        final List<Author> filteredAuthors = authorPage.getContent();

        final Map<String, Object> mockResponse = new HashMap<>();

        mockResponse.put("authors", filteredAuthors);
        mockResponse.put("totalElements", authorPage.getTotalElements());
        mockResponse.put("totalPages", authorPage.getTotalPages());
        mockResponse.put("currentPage", authorPage.getNumber());
        mockResponse.put("pageSize", authorPage.getSize());

        when(authorService.getAllAuthors(page, size)).thenReturn(mockResponse);

        // Act
        final ResponseEntity<Map<String, Object>> responseEntity = authorControllerUnderTest.getAllAuthors(page, size);

        // Assert
        verify(authorService, times(1)).getAllAuthors(page, size);
        assertSame(mockResponse, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    void FilterAuthors() {
        // Arrange
        final int page = 0;
        final int size = 25;

        final Page<Author> authorPage = new PageImpl<>(authors, PageRequest.of(page, size),100L);
        final List<Author> filteredAuthors = authorPage.getContent();

        final Map<String, Object> mockResponse = new HashMap<>();

        mockResponse.put("authors", filteredAuthors);
        mockResponse.put("totalElements", authorPage.getTotalElements());
        mockResponse.put("totalPages", authorPage.getTotalPages());
        mockResponse.put("currentPage", authorPage.getNumber());
        mockResponse.put("pageSize", authorPage.getSize());

        when(authorService.filterAuthors(authors.get(0).getFirstname(), authors.get(0).getLastname(), page, size)).thenReturn(mockResponse);

        // Act
        final ResponseEntity<Map<String, Object>> responseEntity = authorControllerUnderTest.filterAuthors(authors.get(0).getFirstname(), authors.get(0).getLastname(), page, size);

        // Assert
        verify(authorService, times(1)).filterAuthors(authors.get(0).getFirstname(), authors.get(0).getLastname(), page, size);
        assertSame(mockResponse, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    void SaveOrUpdateAuthor() {
        // Arrange
        final Author mockAuthor = authors.get(0);

        when(authorService.saveOrUpdateAuthor(mockAuthor)).thenReturn(mockAuthor);

        // Act
        final ResponseEntity<Author> responseEntity = authorControllerUnderTest.saveOrUpdateAuthor(mockAuthor);

        // Assert
        verify(authorService, times(1)).saveOrUpdateAuthor(mockAuthor);
        assertSame(mockAuthor, responseEntity.getBody());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }


    @Test
    void DeleteAuthorById() {
        // Arrange
        final Long authorId = 1L;

        // Act
        final ResponseEntity<Void> responseEntity = authorControllerUnderTest.deleteAuthorById(authorId);

        // Assert
        verify(authorService, times(1)).deleteAuthorById(authorId);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}
