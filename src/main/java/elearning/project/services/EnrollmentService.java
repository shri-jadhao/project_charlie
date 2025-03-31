package elearning.project.services;

import elearning.project.modelDTO.EnrollmentDTO;
import elearning.project.models.Enrollment;

import java.util.List;
import java.util.Optional;

public interface EnrollmentService {
    List<EnrollmentDTO> getAllEnrollments();
    Optional<EnrollmentDTO> getEnrollmentById(Long id);
    Enrollment saveEnrollment(Enrollment enrollment);
    void deleteEnrollment(Long id);
}