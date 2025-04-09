package elearning.project.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import elearning.project.controller.CourseController;
import elearning.project.exceptions.CourseIdNotFoundException;
import elearning.project.exceptions.ResourceIdNotFoundException;
import elearning.project.exceptions.RoleBasedAccessControlException;
import elearning.project.exceptions.UserAlreadyExists;
import elearning.project.modelDTO.CourseDTO;
import elearning.project.models.Course;
import elearning.project.models.User;
import elearning.project.repositories.CourseRepo;
import elearning.project.repositories.UserRepo;
import elearning.project.securitypriciples.UserPrincipals;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImp implements CourseService {


    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImp.class);

    @Autowired
    private CourseRepo courseRepository;
    @Autowired
    private UserRepo userRepository;

    @Override
    public List<CourseDTO> getAllCourses() {
        logger.info("Fetching all courses");
        return courseRepository.findAll().stream().map(course->convertToDTO(course)).collect(Collectors.toList());
    }

    @Override
    public Optional<CourseDTO> getCourseById(Long id) {
        logger.info("Fetching course with ID: {}", id);
        if(courseRepository.findById(id).isEmpty()) {
        	throw new CourseIdNotFoundException("Course Id Not Found");
        }
        return courseRepository.findById(id).map(course->convertToDTO(course));
    }
    
    @Override
    public Course saveCourse(Course course) {
    	
        logger.info("Saving course with instructor ID: {}", course.getInstructor().getUserID());
        if(!courseRepository.findByTitle(course.getTitle()).isEmpty()) {
        	throw new UserAlreadyExists("Course already exists try different course!");
        }
        if (userRepository.findById(course.getInstructor().getUserID()).isEmpty()) {
            logger.error("Instructor with ID: {} not found", course.getInstructor().getUserID());

            throw new ResourceIdNotFoundException("Sorry the user/instructor not found!");
        }
        else if(!userRepository.findById(course.getInstructor().getUserID()).get().getRole().equals(User.Role.INSTRUCTOR)) {
        	throw new ResourceIdNotFoundException("User is not the instructor!");
        }
        return courseRepository.save(course);
    }
    
    @Override
    public void deleteCourse(Long id) {
        logger.info("Deleting course with ID: {}", id);
        courseRepository.deleteById(id);
    }

//  @PreAuthorize()
    @Override
    public Course updateCourse(Long id, Course course){
    	System.out.println("Hello in update course");
    	if(getLoggerDetails().getUser().getUserID()!=course.getInstructor().getUserID()) {
    		throw new RoleBasedAccessControlException("You have no permission to edit the courses!");
    	}
    	System.out.println("Next");
        Optional<Course> existingCourse = courseRepository.findById(id);
        if (existingCourse.isEmpty()) {
            throw new ResourceIdNotFoundException("Course not found with id: " + id);
        }
        System.out.println("That Next");
        Course updatedCourse = existingCourse.get();
        updatedCourse.setTitle(course.getTitle());
        updatedCourse.setDescription(course.getDescription());
        updatedCourse.setInstructor(course.getInstructor());
        return courseRepository.save(updatedCourse);
    }
    
    private CourseDTO convertToDTO(Course course) {
    	 List<Long>videoIDS=course.getVideo().stream().map(video->video.getVidoeid()).collect(Collectors.toList());
    	  return new CourseDTO(
    			  course.getCourseID(),
    			  course.getTitle(),
    			  course.getDescription(),
    			  course.getInstructor().getUserID(),
    			  videoIDS
          );
    }
    
    public UserPrincipals getLoggerDetails() {
         UserPrincipals userdetails= (UserPrincipals) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         return userdetails;
    }

}




