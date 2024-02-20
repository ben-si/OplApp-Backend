package org.oplapp.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.oplapp.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.data.domain.*;

import java.util.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepositoryUnderTest;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;


    @Test
    void queryBooks_ShouldReturnFilteredBooks() {
        // Arrange
        final int page = 0;
        final int size = 10;

        final Author testAuthor = new Author("Andrea", "Meyer");
        final Author savedAuthor = authorRepository.save(testAuthor);

        final Publisher testPublisher = new Publisher(1L, "Test Publisher");
        final Publisher savedPublisher = publisherRepository.save(testPublisher);

        final Book book1 = new Book("Test Book1", Set.of(savedAuthor), savedPublisher);
        final Book book2 = new Book("Test Book2", Set.of(testAuthor), savedPublisher);

        final Book savedBook1 = bookRepositoryUnderTest.save(book1);
        final Book savedBook2 = bookRepositoryUnderTest.save(book2);

        // Act
        final Page<Book> resultPage = bookRepositoryUnderTest.queryBooks("1", null, null , "Meyer", null, null, null, null, null, null, null, null, PageRequest.of(page, size));

        // Assert
        final List<Book> books = resultPage.getContent();

        assertEquals(1, books.size());
        assertEquals(savedBook1, books.get(0));
    }
}
