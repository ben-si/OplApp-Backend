CREATE DATABASE IF NOT EXISTS Books_library CHARACTER SET latin1 COLLATE latin1_german2_ci;

USE Books_library;

CREATE TABLE Author (
    author_id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL
);

CREATE TABLE Publisher (
    publisher_id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    publisher_name VARCHAR(128) NOT NULL
);

CREATE TABLE Book (
    book_id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    book_title VARCHAR(255) NOT NULL,
    edition VARCHAR(64),
    genre VARCHAR(64),
    publishing_year SMALLINT,
    page_count SMALLINT,
    isbn VARCHAR(64),
    notes VARCHAR(255),
    publisher_id BIGINT NOT NULL,
    shelf VARCHAR(8),
    ledge VARCHAR(8)
);

CREATE TABLE Tag (
    tag_id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    tag_name VARCHAR(64) NOT NULL
);

CREATE TABLE Book_Tags (
    book_tags_id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    book_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    FOREIGN KEY (book_id) REFERENCES Book (book_id),
    FOREIGN KEY (tag_id) REFERENCES Tag (tag_id)
);

CREATE TABLE Book_Authors (
    book_authors_id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    book_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    FOREIGN KEY (book_id) REFERENCES Book (book_id),
    FOREIGN KEY (author_id) REFERENCES Author (author_id)
);


-- Procedures

DELIMITER //

CREATE PROCEDURE getTotalBooksCount(OUT totalBooks INTEGER)
BEGIN
    SELECT count(book_id) FROM Book;
END //


CREATE PROCEDURE getTotalAuthorsCount(OUT totalAuthors INTEGER)
BEGIN
    SELECT count(author_id) FROM Author;
END //


CREATE PROCEDURE getTotalPublishersCount(OUT totalPublishers INTEGER)
BEGIN
    SELECT count(publisher_id) FROM Publisher;
END //

DELIMITER ;
