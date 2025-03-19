package elearning.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import elearning.project.models.Assessment;
import elearning.project.repositories.AssessmentRepo;

import java.util.List;
import java.util.Optional;

@Service
public class AssessmentServiceImp implements AssessmentService {

    @Autowired
    private AssessmentRepo assessmentRepository;

    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }

    public Optional<Assessment> getAssessmentById(Long id) {
        return assessmentRepository.findById(id);
    }

    public Assessment saveAssessment(Assessment assessment) {
        return assessmentRepository.save(assessment);
    }

    public void deleteAssessment(Long id) {
        assessmentRepository.deleteById(id);
    }
}