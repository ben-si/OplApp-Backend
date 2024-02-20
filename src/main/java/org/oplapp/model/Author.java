package org.oplapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * This class models author entities.
 */
@Entity
@Table(name = "Author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long authorId;

    @NotNull(message = "firstname: May not be null")
    @Size(min = 1, message = "firstname: Must have at least 1 character")
    @Column(name = "firstname")
    private String firstname;

    @NotNull(message = "lastname: May not be null")
    @Size(min = 1, message = "lastname: Must have at least 1 character")
    @Column(name = "lastname")
    private String lastname;


    public Author() {
    }

    public Author(final String firstname, final String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Author(final Long authorId, final String firstname, final String lastname) {
        this.authorId = authorId;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(final Long authorId) {
        this.authorId = authorId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }
}
