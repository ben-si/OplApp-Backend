package org.oplapp.repository;

import org.oplapp.model.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.query.*;
import org.springframework.stereotype.*;

/**
 * Repository interface providing publisher related CRUD operations.
 */
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    /**
     * Query's publishers matching the given filter criteria from the database.
     *
     * @param publisherName the given publisher name
     * @return a Page of type Publisher
     */
    @Query("select p from Publisher as p where " +
            "(:publisherName is null or p.publisherName like :publisherName)")
    Page<Publisher> queryPublishers(final String publisherName, final Pageable pageable);


    /**
     * Executes a stored procedure in the database to query the total amount of rows in the publisher table.
     * @return the total number of publishers in the database
     */
    @Procedure("getTotalPublishersCount")
    Integer getTotalPublishersCount();
}
