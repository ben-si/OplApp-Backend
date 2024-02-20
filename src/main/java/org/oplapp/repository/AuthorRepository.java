package org.oplapp.repository;

import org.oplapp.model.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.query.*;
import org.springframework.stereotype.*;

/**
 * Repository interface providing author related CRUD operations.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Query's authors matching the given filter criteria from the database.
     *
     * @param authorFirstname the given author's firstname
     * @param authorLastname  the given author's lastname
     * @return a page of type Author
     */
    @Query("select a from Author as a where " +
            "(:authorFirstname is null or a.firstname like :authorFirstname) and " +
            "(:authorLastname is null or a.lastname like :authorLastname)")
    Page<Author> queryAuthors(final String authorFirstname, final String authorLastname, final Pageable pageable);


    /**
     * Executes a stored procedure in the database to query the total amount of rows in the author table.
     * @return the total number of authors in the database
     */
    @Procedure("getTotalAuthorsCount")
    Integer getTotalAuthorsCount();
}
