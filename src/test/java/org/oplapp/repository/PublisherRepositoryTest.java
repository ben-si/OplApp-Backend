package org.oplapp.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.oplapp.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.data.domain.*;

import java.util.*;

@DataJpaTest
class PublisherRepositoryTest {

    @Autowired
    private PublisherRepository publisherRepositoryUnderTest;


    @Test
    void queryPublishers_ShouldReturnValidPage() {
        // Arrange
        final Publisher publisher1 = new Publisher("Test Publisher");
        final Publisher publisher2 = new Publisher("Test Publisher2");

        publisherRepositoryUnderTest.saveAll(List.of(publisher1, publisher2));

        final Pageable pageable = PageRequest.of(0, 10);

        // Act
        final Page<Publisher> result = publisherRepositoryUnderTest.queryPublishers("Test Publisher", pageable);

        // Assert
        final List<Publisher> publishers = result.getContent();

        assertEquals(1, publishers.size());
        assertEquals(publisher1, publishers.get(0));

    }
}
