package org.oplapp.repository;

import org.oplapp.model.Book;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.query.*;
import org.springframework.stereotype.*;

/**
 * Repository interface providing book related CRUD operations.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Queries all books from the database.
     *
     * @param pageable the given pageable information
     * @return a Page of type Book
     */
    Page<Book> findAll(final Pageable pageable);


    /**
     * Queries books matching the given filter criteria from the database.
     *
     * @param bookTitle       the given book title
     * @param authorId        the given author id
     * @param authorFirstname the given author firstname
     * @param authorLastname  the given author lastname
     * @param publishedBefore the given publishing year min
     * @param publishedAfter  the given publishing year max
     * @param isbn            the given isbn
     * @param publisherId     the given publisher id
     * @param publisher       the given publisher name
     * @param tag             tge given tag
     * @param shelf           the given shelf
     * @param ledge           the given ledge
     * @return a page of type Book
     */
    @Query("select b from Book as b left join b.authors as a left join tags as t where " +
            "(:bookTitle is null or b.bookTitle like '%' || :bookTitle || '%') and " +
            "(:authorId is null or a.authorId = :authorId) and " +
            "(:authorFirstname is null or a.firstname like :authorFirstname) and " +
            "(:authorLastname is null or a.lastname like :authorLastname) and " +
            "(:publishedBefore is null or b.publishingYear >= :publishedBefore) and " +
            "(:publishedAfter is null or b.publishingYear <= :publishedAfter) and " +
            "(:isbn is null or b.isbn like :isbn) and " +
            "(:publisherId is null or b.publisher.publisherId = :publisherId) and " +
            "(:publisher is null or b.publisher.publisherName like :publisher) and " +
            "(:tag is null or t.tagName like :tag) and " +
            "(:shelf is null or b.shelf = :shelf) and " +
            "(:ledge is null or b.ledge = :ledge) " +
            "order by b.bookTitle asc")
    Page<Book> queryBooks(
            final String bookTitle,
            final Long authorId,
            final String authorFirstname,
            final String authorLastname,
            final Integer publishedBefore,
            final Integer publishedAfter,
            final String isbn,
            final Long publisherId,
            final String publisher,
            final String tag,
            final String shelf,
            final String ledge,
            final Pageable pageable
    );


    /**
     * Executes a stored procedure in the database to query the total amount of rows in the book table.
     * @return the total number of books in the database
     */
    @Procedure("getTotalBooksCount")
    Integer getTotalBooksCount();
}
