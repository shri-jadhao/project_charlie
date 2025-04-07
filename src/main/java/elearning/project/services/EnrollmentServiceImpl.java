package elearning.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elearning.project.modelDTO.EnrollmentDTO;
import elearning.project.models.Enrollment;
import elearning.project.repositories.EnrollmentRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private static final Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);

    @Autowired
    private EnrollmentRepo enrollmentRepository;

    public List<EnrollmentDTO> getAllEnrollments() {
        logger.info("Fetching all enrollments");
        return enrollmentRepository.findAll().stream().map(enroll-> convertToDTO(enroll)).collect(Collectors.toList());
    }

    public Optional<EnrollmentDTO> getEnrollmentById(Long id) {
        logger.info("Fetching enrollment with ID: {}", id);
        return enrollmentRepository.findById(id).map(enroll->convertToDTO(enroll));
    }

    public Enrollment saveEnrollment(Enrollment enrollment) {
        logger.info("Saving enrollment with ID: {}", enrollment);
        return enrollmentRepository.save(enrollment);
    }

    public void deleteEnrollment(Long id) {
        logger.info("Deleting enrollment with ID: {}", id);
        enrollmentRepository.deleteById(id);
    }
    public EnrollmentDTO convertToDTO(Enrollment enrollment) {
        return new EnrollmentDTO(
            enrollment.getEnrollmentId(),
            enrollment.getProgress(),
            enrollment.getCourse().getCourseID(),   // Extract only Course ID
            enrollment.getStudent().getUserID()     // Extract only Student ID
        );
    }
}