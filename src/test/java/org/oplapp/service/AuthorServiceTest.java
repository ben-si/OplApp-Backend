package org.oplapp.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.*;
import org.oplapp.exceptions.*;
import org.oplapp.model.*;
import org.oplapp.repository.*;
import org.oplapp.validator.*;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private ValidationHandler<Author> validationHandler;

    private AuthorService authorServiceUnderTest;


    @BeforeEach
    void setup() {
        authorServiceUnderTest = new AuthorService(authorRepository, validationHandler);
    }


    @Test
    void getAuthorById_ShouldReturnAnAuthorIfIdExists() {
        // Arrange
        final Author testAuthor = new Author(1L,"Andrea", "Meyer");
        final Long authorId = testAuthor.getAuthorId();

        when(authorRepository.findById(authorId)).thenReturn(java.util.Optional.of(testAuthor));

        // Act
        final Author resultAuthor = authorServiceUnderTest.getAuthorById(authorId);

        // Assert
        assertEquals(testAuthor, resultAuthor);
    }


    @Test
    void getAuthorById_ShouldThrowAnExceptionIfIdDoesNotExist() {
        // Arrange
        final Long notExistingAuthorId = 99999L;

        when(authorRepository.findById(notExistingAuthorId)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(AuthorNotFoundException.class, () -> authorServiceUnderTest.getAuthorById(notExistingAuthorId));
    }


    @Test
    void getAllAuthors_ShouldReturnAMapWithAuthorsAndPaginationInfo() {
        // Arrange
        final int page = 0;
        final int size = 25;

        final List<Author> mockAuthors = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            mockAuthors.add(new Author("Author", "LastName" + i));
        }

        final Page<Author> mockPage = new PageImpl<>(mockAuthors, PageRequest.of(page, size), 100L);

        when(authorRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        // Act
        final Map<String, Object> result = authorServiceUnderTest.getAllAuthors(page, size);

        // Assert
        assertTrue(result.containsKey("authors"));
        assertTrue(result.containsKey("totalElements"));
        assertTrue(result.containsKey("totalPages"));
        assertTrue(result.containsKey("currentPage"));
        assertTrue(result.containsKey("pageSize"));

        assertEquals(mockAuthors, result.get("authors"));
        assertEquals(100L, result.get("totalElements"));
        assertEquals(4, result.get("totalPages"));
        assertEquals(1, result.get("currentPage"));
        assertEquals(25, result.get("pageSize"));
    }


    @Test
    void filterAuthors_ShouldReturnAMapWithFilteredAuthorsAndPaginationInfo() {
        // Arrange
        final int page = 0;
        final int size = 25;

        final String authorFirstname = "Andrea";
        final String authorLastname = "Meyer";

        final List<Author> mockFilteredAuthors = new ArrayList<>();
        mockFilteredAuthors.add(new Author(authorFirstname, authorLastname));

        final Page<Author> mockPage = new PageImpl<>(mockFilteredAuthors, PageRequest.of(page, size), 1L);

        when(authorRepository.queryAuthors(eq(authorFirstname), eq(authorLastname), any(Pageable.class)))
                .thenReturn(mockPage);

        // Act
        final Map<String, Object> result = authorServiceUnderTest.filterAuthors(authorFirstname, authorLastname, page, size);

        // Assert
        assertTrue(result.containsKey("authors"));
        assertTrue(result.containsKey("totalElements"));
        assertTrue(result.containsKey("totalPages"));
        assertTrue(result.containsKey("currentPage"));
        assertTrue(result.containsKey("pageSize"));

        assertEquals(mockFilteredAuthors, result.get("authors"));
        assertEquals(1L, result.get("totalElements"));
        assertEquals(1, result.get("totalPages"));
        assertEquals(1, result.get("currentPage"));
        assertEquals(25, result.get("pageSize"));
    }


    @Test
    void saveOrUpdateAuthor_ShouldValidateTheGivenAuthorInstanceAndReturnTheSavedAuthor() {
        // Arrange
        final Author inputAuthor = new Author(1L, "Andrea", "Meyer");
        final Author savedAuthor = new Author(1L, "Andrea", "Meyer");

        when(authorRepository.save(inputAuthor)).thenReturn(savedAuthor);

        // Act
        final Author resultAuthor = authorServiceUnderTest.saveOrUpdateAuthor(inputAuthor);

        // Assert
        assertEquals(savedAuthor, resultAuthor);

        verify(validationHandler, times(1)).handleValidation(inputAuthor);
        verify(authorRepository, times(1)).save(inputAuthor);
    }


    @Test
    void deleteAuthorById_DeletesAuthorIfIdExists() {
        // Arrange
        final Long authorId = 1L;

        when(authorRepository.existsById(authorId)).thenReturn(true);

        // Act
        authorServiceUnderTest.deleteAuthorById(authorId);

        // Assert
        verify(authorRepository, times(1)).deleteById(authorId);
    }


    @Test
    void deleteAuthorById_ThrowsExceptionIfIdDoesNotExist() {
        // Arrange
        final Long notExistingAuthorId = 99999L;

        when(authorRepository.existsById(notExistingAuthorId)).thenReturn(false);

        // Act and Assert
        assertThrows(AuthorNotFoundException.class, () -> authorServiceUnderTest.deleteAuthorById(notExistingAuthorId));
        verify(authorRepository, never()).deleteById(notExistingAuthorId);
    }
}
