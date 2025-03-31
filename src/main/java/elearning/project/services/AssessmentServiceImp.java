package elearning.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elearning.project.controller.AssessmentController;
import elearning.project.exceptions.ResourceIdNotFoundException;
import elearning.project.exceptions.RoleBasedAccessControlException;
import elearning.project.modelDTO.AssessmentDTO;
import elearning.project.modelDTO.SubmissionDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import elearning.project.models.Assessment;
import elearning.project.repositories.AssessmentRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class AssessmentServiceImp implements AssessmentService {

    private static final Logger logger = LoggerFactory.getLogger(AssessmentServiceImp.class);

    @Autowired
    private AssessmentRepo assessmentRepository;

    @Override
    public List<AssessmentDTO> getAllAssessments() {
        logger.info("Fetching all assessments");
        return assessmentRepository.findAll().stream().map(assess->convertToDTO(assess)).collect(Collectors.toList());
    }

    @Override
    public Optional<AssessmentDTO> getAssessmentById(Long id) {
        logger.info("Fetching assessment with ID: {}", id);
        return assessmentRepository.findById(id).map(assess-> convertToDTO(assess));
    }

    @Override
    public Assessment saveAssessment(Assessment assessment) {
        logger.info("Saving assessment with ID: {}", assessment);
        return assessmentRepository.save(assessment);
    }

    @Override
    public void deleteAssessment(Long id) {
        logger.info("Deleting assessment with ID: {}", id);
        assessmentRepository.deleteById(id);
    }

    @Override
    public Assessment updateAssessment(Long id, Assessment assessment,int instructorid) {
        Optional<Assessment> existingAssessment = assessmentRepository.findById(id);
        if(instructorid!=existingAssessment.get().getCourse().getInstructor().getUserID()) {
        	throw new RoleBasedAccessControlException("You are not the instructor of this course!");
        }
        else if (existingAssessment.isEmpty()) {
            throw new ResourceIdNotFoundException("Assessment not found with id: " + id);
        }
        Assessment updatedAssessment = existingAssessment.get();
        updatedAssessment.setMaxscore(assessment.getMaxscore());
        updatedAssessment.setCourse(assessment.getCourse());
        // Update other fields as necessary
        return assessmentRepository.save(updatedAssessment);
    }
    public AssessmentDTO convertToDTO(Assessment assessment) {
        // Convert Submission List -> SubmissionDTO List
        List<SubmissionDTO> submissionDTOs = assessment.getSubmission().stream()
                .map(sub -> new SubmissionDTO(sub.getSubmissionId(), sub.getScore(), sub.getStudent().getUserID(),sub.getAssessment().getAssessmentID()))
                .collect(Collectors.toList());

        // Convert Assessment -> AssessmentDTO
        return new AssessmentDTO(
                assessment.getAssessmentID(),
                assessment.getCourse().getCourseID(),  // Extract only Course ID
                assessment.getRole().name(),
                assessment.getMaxscore(),
                submissionDTOs
        );
    }
}
