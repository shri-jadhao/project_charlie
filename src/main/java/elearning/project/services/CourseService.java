package elearning.project.services;

import elearning.project.models.Course;
import elearning.project.exceptions.ResourceIdNotFoundException;
import elearning.project.modelDTO.CourseDTO;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<CourseDTO> getAllCourses();
    Optional<CourseDTO> getCourseById(Long id);
    Course saveCourse(Course course) throws ResourceIdNotFoundException;
    Course updateCourse(Long id, Course course) throws ResourceIdNotFoundException;
    void deleteCourse(Long id);
}
