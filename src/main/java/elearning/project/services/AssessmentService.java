package elearning.project.services;

import elearning.project.modelDTO.AssessmentDTO;
import elearning.project.models.Assessment;

import java.util.List;
import java.util.Optional;

public interface AssessmentService {
    List<AssessmentDTO> getAllAssessments();
    Optional<AssessmentDTO> getAssessmentById(Long id);
    Assessment saveAssessment(Assessment assessment);
    Assessment updateAssessment(Long id, Assessment assessment,int instructorid);
    void deleteAssessment(Long id);
    List<AssessmentDTO> getAllAssessmentsByCourseId(Long cid);
}
