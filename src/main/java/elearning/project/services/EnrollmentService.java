package elearning.project.services;

import elearning.project.models.Enrollment;

import java.util.List;
import java.util.Optional;

public interface EnrollmentService {
    List<Enrollment> getAllEnrollments();
    Optional<Enrollment> getEnrollmentById(Long id);
    Enrollment saveEnrollment(Enrollment enrollment);
    void deleteEnrollment(Long id);
}