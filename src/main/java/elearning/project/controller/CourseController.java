package elearning.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import elearning.project.modelDTO.AssessmentDTO;
import elearning.project.modelDTO.CourseDTO;
import elearning.project.models.Assessment;
import elearning.project.models.Course;
import elearning.project.services.CourseService;
import jakarta.validation.Valid;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        Optional<CourseDTO> course = courseService.getCourseById(id);
        return new ResponseEntity<>(course.get(),HttpStatus.ACCEPTED);
    }
// Only INSTRUCTOR role can access this
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public Course createCourse(@Valid @RequestBody Course course) {
        return courseService.saveCourse(course);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable Long id,@RequestBody Course course){
    	return courseService.updateCourse(id, course);
    }
    
    
    
}
