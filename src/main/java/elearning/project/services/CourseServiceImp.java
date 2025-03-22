package elearning.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elearning.project.exceptions.ResourceIdNotFoundException;
import elearning.project.models.Course;
import elearning.project.repositories.CourseRepo;
import elearning.project.repositories.UserRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImp implements CourseService {

    @Autowired
    private CourseRepo courseRepository;
    @Autowired
    private UserRepo userRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course saveCourse(Course course) {
    	System.out.println("hello bro in creation");
    	System.out.println("User:"+course.getInstructor().getUserID()+"id bro");
    	if(userRepository.findById(course.getInstructor().getUserID()).isEmpty()) {
    		System.out.println("error bro");
    		throw new ResourceIdNotFoundException("Sorry the user/instructor not found!");
    	}
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}