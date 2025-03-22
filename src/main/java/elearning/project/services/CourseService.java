package elearning.project.services;

import elearning.project.models.Course;
import elearning.project.exceptions.ResourceIdNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> getAllCourses();
    Optional<Course> getCourseById(Long id);
    Course saveCourse(Course course) throws ResourceIdNotFoundException;
    void deleteCourse(Long id);
}