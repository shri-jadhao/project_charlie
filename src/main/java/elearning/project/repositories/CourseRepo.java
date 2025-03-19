package elearning.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import elearning.project.models.Course;

public interface CourseRepo extends JpaRepository<Course, Long>{

}
