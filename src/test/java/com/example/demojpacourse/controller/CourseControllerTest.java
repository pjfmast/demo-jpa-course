package com.example.demojpacourse.controller;

import com.example.demojpacourse.model.Course;
import com.example.demojpacourse.repository.CourseRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseControllerTest {

    @Autowired
    private CourseRepository repository;
    Course course1 = new Course("t1", "d1", 10, LocalDate.now(), LocalDate.now());
    Course course2 = new Course("t2", "d2", 10, LocalDate.now(), LocalDate.now());

//    @Before
//    public void setUp() {
//        repository.save(course1);
//        repository.save(course2);
//    }

    @Test
    public void whenDeleteByIdFromRepository_thenDeletingShouldBeSuccessful() {
        // arrange
        long countBefore = repository.count();
        repository.save(course1);
        repository.save(course2);

        //act
        repository.deleteById(course1.getId());

        //assert
        assertThat(repository.count()).isEqualTo(countBefore + 1);
    }
}