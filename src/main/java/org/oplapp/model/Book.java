package org.oplapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.*;

/**
 * This class models book entities.
 */
@Entity
@Table(name = "Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @NotNull(message = "bookTitle: May not be null")
    @Size(min = 1, message = "bookTitle: Must have at least 1 character")
    @Column(name = "book_title")
    private String bookTitle;

    @Size(min = 1, message = "edition: Must have at least 1 character")
    @Column(name = "edition")
    private String edition;

    @Size(min = 1, message = "genre: Must have at least 1 character")
    @Column(name = "genre")
    private String genre;

    @NotNull(message = "authors: May not be null")
    @ManyToMany
    @JoinTable(name = "Book_Authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();

    @Min(value = 0, message = "publishingYear: Must be greater than 0")
    @Max(value = 9999, message = "publishingYear: Must be smaller than 9999")
    @Column(name = "publishing_year")
    private Integer publishingYear;

    @Positive(message = "pageCount: Must have a positive value")
    @Column(name = "page_count")
    private Integer pageCount;

    @Size(min = 9, message = "isbn: Must have at least 9 characters")
    @Column(name = "isbn")
    private String isbn;

    @Size(max = 255, message = "notes: Exceeds the limit of 255 characters")
    @Column(name = "notes")
    private String notes;

    @NotNull(message = "publisher: May not be null")
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToMany
    @JoinTable(name = "Book_Tags",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @Size(min = 1, message = "shelf: Must have at least 1 character")
    @Column(name = "shelf")
    private String shelf;

    @Size(min = 1, message = "ledge: Must have at least 1 character")
    @Column(name = "ledge")
    private String ledge;


    public Book() {
    }

    public Book(String bookTitle, Set<Author> authors, Publisher publisher) {
        this.bookTitle = bookTitle;
        this.authors = authors;
        this.publisher = publisher;
    }

    public Book(Long bookId, String bookTitle, String edition, String genre, Set<Author> authors, Integer publishingYear, Integer pageCount, String isbn, String notes, Publisher publisher, Set<Tag> tags, String shelf, String ledge) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.edition = edition;
        this.genre = genre;
        this.authors = authors != null ? authors : new HashSet<>();
        this.publishingYear = publishingYear;
        this.pageCount = pageCount;
        this.isbn = isbn;
        this.notes = notes;
        this.publisher = publisher;
        this.tags = tags != null ? tags : new HashSet<>();
        this.shelf = shelf;
        this.ledge = ledge;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public int getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(Integer publishingYear) {
        this.publishingYear = publishingYear;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getLedge() {
        return ledge;
    }

    public void setLedge(String ledge) {
        this.ledge = ledge;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }
}
