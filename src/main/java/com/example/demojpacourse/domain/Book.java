package com.example.demojpacourse.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Book {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String isbn;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    public Book() {
    }


    public Book(String isbn, String title, Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course property of new Book cannot be null");
        }
        this.isbn = isbn;
        this.title = title;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(isbn, book.isbn) && Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn, title);
    }
}
