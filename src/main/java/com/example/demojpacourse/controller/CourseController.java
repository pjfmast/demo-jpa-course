package com.example.demojpacourse.controller;

import com.example.demojpacourse.model.Course;
import com.example.demojpacourse.repository.CourseRepository;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CourseController {

    private final CourseRepository courseRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public CourseController( CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> all(@RequestParam(required = false) String title) {
        List<Course> found = new ArrayList<>();

        if (title == null) {
            found.addAll(courseRepository.findAll());
        } else {
            found.addAll(courseRepository.findByTitleContainingIgnoringCase(title));
        }
        if (found.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(found);
    }

    @GetMapping("/courses/upcoming")
    public ResponseEntity<List<Course>> upComingCourses() {
        List<Course> found = new ArrayList<>();
        found.addAll(courseRepository.findByStartDateAfter(LocalDate.now()));

        if (found.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(found);
    }

    @PostMapping("/courses")
    ResponseEntity<Course> newCourse(@RequestBody Course newCourse) {

        try {
            Course _course = courseRepository.save(newCourse);
            return new ResponseEntity<>(_course, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error during creation of: " + newCourse, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable Long id) {
        if ((!courseRepository.existsById(id))) {
            // HTTP 404 Not Found response is for (deleting) a resource does not exist
            return ResponseEntity.notFound().build();
        }
        courseRepository.deleteById(id);
        // HTTP 204 No Content: The server successfully processed the request, but is not returning any content
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/courses/{id}")
    ResponseEntity<Course> updateCourse(@RequestBody Course newCourse, @PathVariable Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);

        if (optionalCourse.isPresent()) {
            Course _course = optionalCourse.get();
            _course.setTitle(newCourse.getTitle());
            _course.setCapacity(newCourse.getCapacity());
            _course.setDescription(newCourse.getDescription());
            _course.setStartDate(newCourse.getStartDate());
            _course.setEndDate(newCourse.getEndDate());

            return ResponseEntity.ok(courseRepository.save(_course));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
