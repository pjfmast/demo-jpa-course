package com.example.demojpacourse.repository;

import com.example.demojpacourse.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoringCase(String title);

}
