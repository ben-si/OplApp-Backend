package org.oplapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * This class models tag entities.
 */
@Entity
@Table(name = "Tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @NotNull(message = "tagName: May not be null")
    @Size(min = 2, message = "tagName: Must have at least 2 character")
    @Column(name = "tag_name")
    private String tagName;


    public Tag() {
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public Tag(Long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
