package org.oplapp.repository;

import org.junit.jupiter.api.*;
import org.oplapp.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepositoryUnderTest;


    @Test
    void queryAuthors_ShouldReturnFilteredAuthors() {
        // Arrange
        final Author author1 = new Author("Andreas", "Meyer");
        final Author author2 = new Author("Andrea", "Meyer");

        authorRepositoryUnderTest.saveAll(List.of(author1, author2));

        // Act
        final Page<Author> resultPage = authorRepositoryUnderTest.queryAuthors("Andrea", "Meyer", PageRequest.of(0, 10));
        // Assert
        final List<Author> authors = resultPage.getContent();

        assertEquals(1, authors.size());
        assertEquals(author2, authors.get(0));
    }


    @Test
    void queryAuthors_ReturnsAllAuthors_WhenNoFilter() {
        // Arrange
        final Author author1 = new Author("Andreas", "Meyer");
        final Author author2 = new Author("Andrea", "Meyer");

        authorRepositoryUnderTest.saveAll(List.of(author1, author2));

        // Act
        final Page<Author> resultPage = authorRepositoryUnderTest.queryAuthors(null, null, PageRequest.of(0, 10));

        // Assert
        final List<Author> authors = resultPage.getContent();

        assertEquals(2, authors.size());
        assertTrue(authors.contains(author1));
        assertTrue(authors.contains(author2));
    }
}
