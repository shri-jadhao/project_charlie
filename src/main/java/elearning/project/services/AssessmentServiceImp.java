package elearning.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import elearning.project.controller.AssessmentController;
import elearning.project.exceptions.ResourceIdNotFoundException;
import elearning.project.exceptions.RoleBasedAccessControlException;
import elearning.project.models.Assessment;
import elearning.project.repositories.AssessmentRepo;

import java.util.List;
import java.util.Optional;

@Service
public class AssessmentServiceImp implements AssessmentService {

    @Autowired
    private AssessmentRepo assessmentRepository;

    @Override
    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }

    @Override
    public Optional<Assessment> getAssessmentById(Long id) {
        return assessmentRepository.findById(id);
    }

    @Override
    public Assessment saveAssessment(Assessment assessment) {
        return assessmentRepository.save(assessment);
    }

    @Override
    public void deleteAssessment(Long id) {
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
}
