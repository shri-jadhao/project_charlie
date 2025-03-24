package elearning.project.services;

import elearning.project.models.Assessment;

import java.util.List;
import java.util.Optional;

public interface AssessmentService {
    List<Assessment> getAllAssessments();
    Optional<Assessment> getAssessmentById(Long id);
    Assessment saveAssessment(Assessment assessment);
    Assessment updateAssessment(Long id, Assessment assessment,int instructorid);
    void deleteAssessment(Long id);
}
