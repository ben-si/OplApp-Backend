package org.oplapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * This class models publisher entities.
 */
@Entity
@Table(name = "Publisher")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    private Long publisherId;

    @NotNull(message = "publisherName: May not be null")
    @Size(min = 2, message = "publisherName: Must have at least 2 characters")
    @Column(name = "publisher_name")
    private String publisherName;


    public Publisher() {
    }

    public Publisher(final String publisherName) {
        this.publisherName = publisherName;
    }

    public Publisher(final Long publisherId, final String publisherName) {
        this.publisherId = publisherId;
        this.publisherName = publisherName;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(final Long publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(final String publisherName) {
        this.publisherName = publisherName;
    }
}