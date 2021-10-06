package com.example.demojpacourse;

import com.example.demojpacourse.domain.Course;
import com.example.demojpacourse.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

// Annotating a class:
// - with the @Configuration indicates that the class can be used by the Spring IoC container as a source of bean definitions.
// - The @Bean annotation tells Spring that a method annotated with @Bean will return an object that should be registered as a bean in the Spring application context.

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CourseRepository repository) {
        return args -> {
          log.info("Preloading " + repository.save(new Course("Introduction to Java", "all yo need to know to create...", 64, LocalDate.of(2021,9,1), LocalDate.of(2021,11,6))));
          log.info("Preloading " + repository.save(new Course("Java and mobile", "Create a cool Android app...", 64, LocalDate.of(2021,11,8), LocalDate.of(2022,1,29))));
          log.info("Preloading " + repository.save(new Course("asp.net", "Create web application in C#...", 64, LocalDate.of(2022,2,1), LocalDate.of(2021,11,6))));
          log.info("Preloading " + repository.save(new Course("Xamarin and Maui", "create a multi platform mobile app", 64, LocalDate.of(2022,4,1), LocalDate.of(2021,11,6))));
        };

    }
}
