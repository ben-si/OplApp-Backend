package org.oplapp.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.oplapp.exceptions.*;
import org.oplapp.model.*;
import org.oplapp.repository.*;
import org.oplapp.validator.*;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private ValidationHandler<Book> validationHandler;

    private BookService bookServiceUnderTest;
    private Author testAuthor;
    private Publisher testPublisher;
    private Book testBook;


    @BeforeEach
    void setup() {
        bookServiceUnderTest = new BookService(bookRepository, validationHandler);
        testAuthor = new Author(1L, "Andrea", "Meyer");
        testPublisher = new Publisher(1L, "Test Publisher");
        testBook = new Book(1L, "Test Book", null, null, Set.of(testAuthor), 2022, 280, null, null, testPublisher, null, null, null);
    }


    @Test
    void getBookById_ShouldReturnABookIfIdExists() {
        // Arrange
        final Book testBook = this.testBook;
        final Long bookId = testBook.getBookId();

        when(bookRepository.findById(bookId)).thenReturn(java.util.Optional.of(testBook));

        // Act
        final Book resultBook = bookServiceUnderTest.getBookById(bookId);

        // Assert
        assertEquals(testBook, resultBook);
    }


    @Test
    void getBookById_ShouldThrowAnExceptionIfIdDoesNotExist() {
        // Arrange
        final Long notExistingBookId = 99999L;

        when(bookRepository.findById(notExistingBookId)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(BookNotFoundException.class, () -> bookServiceUnderTest.getBookById(notExistingBookId));
    }


    @Test
    void getAllBooks_ShouldReturnAMapWithBooksAndPaginationInfo() {
        // Arrange
        final int page = 0;
        final int size = 25;

        final List<Book> mockBooks = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            final Book testBook = this.testBook;
            final String renamedBookTitle = this.testBook.getBookTitle() + i;
            testBook.setBookTitle(renamedBookTitle);

            mockBooks.add(testBook);
        }

        final Page<Book> mockPage = new PageImpl<>(mockBooks, PageRequest.of(page, size), 100L);

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        // Act
        final Map<String, Object> result = bookServiceUnderTest.getAllBooks(page, size);

        // Assert
        assertTrue(result.containsKey("books"));
        assertTrue(result.containsKey("totalElements"));
        assertTrue(result.containsKey("totalPages"));
        assertTrue(result.containsKey("currentPage"));
        assertTrue(result.containsKey("pageSize"));

        assertEquals(mockBooks, result.get("books"));
        assertEquals(100L, result.get("totalElements"));
        assertEquals(4, result.get("totalPages"));
        assertEquals(1, result.get("currentPage"));
        assertEquals(25, result.get("pageSize"));
    }


    @Test
    void filterBooks_ShouldReturnAMapWithFilteredBooksAndPaginationInfo() {
        // Arrange
        final int page = 0;
        final int size = 25;

        final String bookTitle = "Test Book";

        final List<Book> mockFilteredBooks = new ArrayList<>();
        mockFilteredBooks.add(this.testBook);
        final Page<Book> mockPage = new PageImpl<>(mockFilteredBooks, PageRequest.of(page, size), 1L);

        when(bookRepository.queryBooks(eq(bookTitle), eq(null), eq(null), eq(null), eq(null), eq(null), eq(null), eq(null), eq(null), eq(null), eq(null), eq(null), any(Pageable.class)))
                .thenReturn(mockPage);

        // Act
        final Map<String, Object> result = bookServiceUnderTest.filterBooks(bookTitle, null, null, null, null, null, null, null, null, null, null, null, page, size);

        // Assert
        assertTrue(result.containsKey("books"));
        assertTrue(result.containsKey("totalElements"));
        assertTrue(result.containsKey("totalPages"));
        assertTrue(result.containsKey("currentPage"));
        assertTrue(result.containsKey("pageSize"));

        assertEquals(mockFilteredBooks, result.get("books"));
        assertEquals(1L, result.get("totalElements"));
        assertEquals(1, result.get("totalPages"));
        assertEquals(1, result.get("currentPage"));
        assertEquals(25, result.get("pageSize"));
    }


    @Test
    void saveOrUpdateBook_ShouldValidateTheGivenBookInstanceAndReturnTheSavedBook() {
        // Arrange
        final Book inputBook = new Book(null, "Test Book1", null, null, Set.of(testAuthor), 2022, 280, null, null, testPublisher, null, null, null);
        final Book persistedBook = new Book(2L, "Test Book2", null, null, Set.of(testAuthor), 2022, 280, null, null, testPublisher, null, null, null);

        when(bookRepository.save(inputBook)).thenReturn(persistedBook);

        // Act
        final Book resultBook = bookServiceUnderTest.saveOrUpdateBook(inputBook);

        // Assert
        assertEquals(persistedBook, resultBook);
        verify(validationHandler, times(1)).handleValidation(inputBook);
        verify(bookRepository, times(1)).save(inputBook);
    }


    @Test
    void deleteBookById_DeletesBookIfIdExists() {
        // Arrange
        final Long bookId = 1L;
        when(bookRepository.existsById(bookId)).thenReturn(true);

        // Act
        bookServiceUnderTest.deleteBookById(bookId);

        // Assert
        verify(bookRepository, times(1)).deleteById(bookId);
    }


    @Test
    void deleteBookById_ThrowsExceptionIfIdDoesNotExist() {
        // Arrange
        final Long notExistingBookId = 99999L;

        when(bookRepository.existsById(notExistingBookId)).thenReturn(false);

        // Act and Assert
        assertThrows(BookNotFoundException.class, () -> bookServiceUnderTest.deleteBookById(notExistingBookId));
        verify(bookRepository, never()).deleteById(notExistingBookId);
    }
}