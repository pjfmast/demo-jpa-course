package com.example.demojpacourse.repository;

import com.example.demojpacourse.domain.Course;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

// see: https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.sql.jpa-and-spring-data.repositories

public interface CourseRepository extends JpaRepository<Course, Long> {
    // By extending JpaRepository we already get a lot of 'find'-functionality

    // Here we put our custom finder methods:
    // Some magic happens: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    List<Course> findByTitleContainingIgnoringCase(String title);
    List<Course> findByStartDateAfter(LocalDate fromDate);

    @Query(value = "SELECT * FROM Course c WHERE DATE(startDate) = ?1", nativeQuery = true)
    List<Course> findStartingToday();

    @Query(value = "SELECT c FROM Course c")
    List<Course> findAllCourses(Sort sort);
}
