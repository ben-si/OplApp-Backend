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
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookControllerUnderTest;
    private List<Book> books;


    @BeforeEach
    void setUp() {
        final Set<Author> testAuthors = Set.of(new Author(1L, "Andrea", "Meyer"));
        final Publisher testPublisher = new Publisher(1L, "Test Publisher");
        books = List.of(new Book(1L, "Test Book", null, null, testAuthors, null, null, null, null, testPublisher, null, null, null));
    }


    @Test
    void GetBookById() {
        // Arrange
        final Long bookId = 1L;
        final Book mockBook = books.get(0);

        when(bookService.getBookById(bookId)).thenReturn(mockBook);

        // Act
        final ResponseEntity<Book> responseEntity = bookControllerUnderTest.getBookById(bookId);

        // Assert
        verify(bookService, times(1)).getBookById(bookId);
        assertSame(mockBook, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    void GetAllBooks() {
        // Arrange
        final int page = 0;
        final int size = 25;

        final Page<Book> bookPage = new PageImpl<>(books, PageRequest.of(page, size),100L);
        final List<Book> books = bookPage.getContent();

        final Map<String, Object> mockResponse = new HashMap<>();

        mockResponse.put("books", books);
        mockResponse.put("totalElements", bookPage.getTotalElements());
        mockResponse.put("totalPages", bookPage.getTotalPages());
        mockResponse.put("currentPage", bookPage.getNumber());
        mockResponse.put("pageSize", bookPage.getSize());

        when(bookService.getAllBooks(page, size)).thenReturn(mockResponse);

        // Act
        final ResponseEntity<Map<String, Object>> responseEntity = bookControllerUnderTest.getAllBooks(page, size);

        // Assert
        verify(bookService, times(1)).getAllBooks(page, size);
        assertSame(mockResponse, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    void FilterBooks() {
        // Arrange
        final int page = 0;
        final int size = 25;

        final Page<Book> bookPage = new PageImpl<>(books, PageRequest.of(page, size),100L);
        final List<Book> filteredBooks = bookPage.getContent();

        final Map<String, Object> mockResponse = new HashMap<>();

        mockResponse.put("books", filteredBooks);
        mockResponse.put("totalElements", bookPage.getTotalElements());
        mockResponse.put("totalPages", bookPage.getTotalPages());
        mockResponse.put("currentPage", bookPage.getNumber());
        mockResponse.put("pageSize", bookPage.getSize());

        when(bookService.filterBooks(books.get(0).getBookTitle(), null, null, null, null, null, null, null, null, null, null, null, page, size)).thenReturn(mockResponse);

        // Act
        final ResponseEntity<Map<String, Object>> responseEntity = bookControllerUnderTest.filterBooks(books.get(0).getBookTitle(), null, null, null, null, null, null, null, null, null, null, null, page, size);

        // Assert
        verify(bookService, times(1)).filterBooks(books.get(0).getBookTitle(), null, null, null, null, null, null, null, null, null, null, null, page, size);
        assertSame(mockResponse, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    void SaveOrUpdateBook() {
        // Arrange
        final Book mockBook = books.get(0);

        when(bookService.saveOrUpdateBook(mockBook)).thenReturn(mockBook);

        // Act
        final ResponseEntity<Book> responseEntity = bookControllerUnderTest.saveOrUpdateBook(mockBook);

        // Assert
        verify(bookService, times(1)).saveOrUpdateBook(mockBook);
        assertSame(mockBook, responseEntity.getBody());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }


    @Test
    void DeleteBookById() {
        // Arrange
        final Long bookId = 1L;

        // Act
        final ResponseEntity<Void> responseEntity = bookControllerUnderTest.deleteBookById(bookId);

        // Assert
        verify(bookService, times(1)).deleteBookById(bookId);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}