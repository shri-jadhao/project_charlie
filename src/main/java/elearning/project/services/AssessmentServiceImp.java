package elearning.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elearning.project.models.Assessment;
import elearning.project.repositories.AssessmentRepo;

import java.util.List;
import java.util.Optional;

@Service
public class AssessmentServiceImp implements AssessmentService {

    private static final Logger logger = LoggerFactory.getLogger(AssessmentServiceImp.class);

    @Autowired
    private AssessmentRepo assessmentRepository;

    public List<Assessment> getAllAssessments() {
        logger.info("Fetching all assessments");
        return assessmentRepository.findAll();
    }

    public Optional<Assessment> getAssessmentById(Long id) {
        logger.info("Fetching assessment with ID: {}", id);
        return assessmentRepository.findById(id);
    }

    public Assessment saveAssessment(Assessment assessment) {
        logger.info("Saving assessment with ID: {}", assessment);
        return assessmentRepository.save(assessment);
    }

    public void deleteAssessment(Long id) {
        logger.info("Deleting assessment with ID: {}", id);
        assessmentRepository.deleteById(id);
    }
}