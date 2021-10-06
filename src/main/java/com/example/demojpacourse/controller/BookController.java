package com.example.demojpacourse.controller;

import com.example.demojpacourse.domain.Book;
import com.example.demojpacourse.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    // @Autowired not needed anymore after Spring 4.3
    private final BookRepository bookRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> all(@RequestParam(required = false) String title) {
        List<Book> found = new ArrayList<>();

        if (title == null) {
            found.addAll(bookRepository.findAll());
        } else {
            found.addAll(bookRepository.findByTitleContainingIgnoringCase(title));
        }
        if (found.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(found);
    }

    @PostMapping("/books")
    ResponseEntity<Book> newBook(@RequestBody Book newBook) {

        try {
            Book _book = bookRepository.save(newBook);
            return new ResponseEntity<>(_book, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error during creation of: " + newBook, e);
            return ResponseEntity.badRequest().build();
        }
    }
}
