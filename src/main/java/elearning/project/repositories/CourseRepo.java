package elearning.project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import elearning.project.models.Course;

public interface CourseRepo extends JpaRepository<Course, Long>{
       Optional<Course> findByTitle(String title);
}
