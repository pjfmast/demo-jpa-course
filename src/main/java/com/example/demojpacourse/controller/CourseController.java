package com.example.demojpacourse.controller;

import com.example.demojpacourse.model.Course;
import com.example.demojpacourse.repository.CourseRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses")
    public List<Course> all(@RequestParam(required = false) String title) {
        List<Course> found = new ArrayList<Course>();

        if (title == null) {
            courseRepository.findAll().forEach(found::add);
        } else {
            courseRepository.findByTitleContainingIgnoringCase(title).forEach(found::add);
        }
        return found;
    }

    @GetMapping("/courses/upcoming")
    public List<Course> upComingCourses() {
        List<Course> found = new ArrayList<Course>();
        courseRepository.findByStartDateAfter(LocalDate.now()).forEach(found::add);

        return found;
    }

    @PostMapping("/courses")
    Course newCourse(@RequestBody Course newCourse) {
        return courseRepository.save(newCourse);
    }

    @DeleteMapping("/courses/{id}")
    void deleteCourse(@PathVariable Long id) {
        courseRepository.deleteById(id);
    }

    @PutMapping("/courses/{id}")
    Course replaceCourse(@RequestBody Course newCourse, @PathVariable Long id) {

        return courseRepository.findById(id)
                .map(course -> {
                    course.setTitle(newCourse.getTitle());
                    course.setCapacity(newCourse.getCapacity());
                    course.setDescription(newCourse.getDescription());
                    course.setStartDate(newCourse.getStartDate());
                    course.setEndDate(newCourse.getEndDate());
                    return courseRepository.save(course);
                })
                .orElseGet(() -> {
                    newCourse.setId(id);
                    return courseRepository.save(newCourse);
                });
    }


}
