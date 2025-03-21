package elearning.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elearning.project.exceptions.ResourceIdNotFoundException;
import elearning.project.models.Course;
import elearning.project.repositories.CourseRepo;
import elearning.project.repositories.UserRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImp implements CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImp.class);

    @Autowired
    private CourseRepo courseRepository;
    @Autowired
    private UserRepo userRepository;

    public List<Course> getAllCourses() {
        logger.info("Fetching all courses");
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        logger.info("Fetching course with ID: {}", id);
        return courseRepository.findById(id);
    }

    public Course saveCourse(Course course) {
        logger.info("Saving course with instructor ID: {}", course.getInstructor().getUserID());
        if (userRepository.findById(course.getInstructor().getUserID()).isEmpty()) {
            logger.error("Instructor with ID: {} not found", course.getInstructor().getUserID());
            throw new ResourceIdNotFoundException("Sorry the user/instructor not found!");
        }
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        logger.info("Deleting course with ID: {}", id);
        courseRepository.deleteById(id);
    }
}