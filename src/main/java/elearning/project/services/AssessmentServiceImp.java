package elearning.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import elearning.project.controller.AssessmentController;
import elearning.project.exceptions.ExistsException;
import elearning.project.exceptions.ResourceIdNotFoundException;
import elearning.project.exceptions.RoleBasedAccessControlException;
import elearning.project.modelDTO.AssessmentDTO;
import elearning.project.modelDTO.CourseDTO;
import elearning.project.modelDTO.SubmissionDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elearning.project.models.Assessment;
import elearning.project.repositories.AssessmentRepo;
import elearning.project.securitypriciples.UserPrincipals;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssessmentServiceImp implements AssessmentService {

	private static final Logger logger = LoggerFactory.getLogger(AssessmentServiceImp.class);

	@Autowired
	private AssessmentRepo assessmentRepository;
	@Autowired
	private CourseService courseService;

	@Override
	public List<AssessmentDTO> getAllAssessments() {
		logger.info("Fetching all assessments");
		return assessmentRepository.findAll().stream().map(assess -> convertToDTO(assess)).collect(Collectors.toList());
	}

	@Override
	public Optional<AssessmentDTO> getAssessmentById(Long id) {
		logger.info("Fetching assessment with ID: {}", id);
		return assessmentRepository.findById(id).map(assess -> convertToDTO(assess));
	}

	@Override
	public Assessment saveAssessment(Assessment assessment) {
		logger.info("Saving assessment with ID: {}", assessment);
		Long courseId = assessment.getCourse().getCourseID();
		Optional<CourseDTO> course = courseService.getCourseById(courseId);
		Long loggerId=getLoggerDetails().getUser().getUserID();
		if (!isCourseInstructor(course, loggerId)) {
			throw new RoleBasedAccessControlException("You are not the instructor of this course!");
		} else if (!assessmentRepository.findByName(assessment.getName()).isEmpty()) {
			throw new ExistsException("Assessment with name " + assessment.getName() + "exists!");
		}
		return assessmentRepository.save(assessment);
	}

	@Override
	public void deleteAssessment(Long id) {
		logger.info("Deleting assessment with ID: {}", id);
		assessmentRepository.deleteById(id);
	}

	@Override
	public Assessment updateAssessment(Long id, Assessment assessment, int instructorid) {
		Optional<Assessment> existingAssessment = assessmentRepository.findById(id);
		if (instructorid != existingAssessment.get().getCourse().getInstructor().getUserID()) {
			throw new RoleBasedAccessControlException("You are not the instructor of this course!");
		} else if (existingAssessment.isEmpty()) {
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
		List<SubmissionDTO> submissionDTOs = assessment
				.getSubmission().stream().map(sub -> new SubmissionDTO(sub.getSubmissionId(), sub.getScore(),
						sub.getStudent().getUserID(), sub.getAssessment().getAssessmentID()))
				.collect(Collectors.toList());

		// Convert Assessment -> AssessmentDTO
		return new AssessmentDTO(assessment.getAssessmentID(), assessment.getName(),
				assessment.getCourse().getCourseID(), // Extract only Course ID
				assessment.getRole().name(), assessment.getMaxscore(), submissionDTOs);
	}

	public UserPrincipals getLoggerDetails() {
		UserPrincipals userdetails = (UserPrincipals) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return userdetails;
	}

	public boolean isCourseInstructor(Optional<CourseDTO> course, Long currentUserId) {
		return course.get().getInstructorID().equals(currentUserId);
	}

	@Override
	public List<AssessmentDTO> getAllAssessmentsByCourseId(Long cid) {
		Optional<CourseDTO> course=courseService.getCourseById(cid);
		Long loggerId=getLoggerDetails().getUser().getUserID();
		if(!isCourseInstructor(course,loggerId)) {
			throw new RoleBasedAccessControlException("You are not the instructor of this course!");
		}
		List<Assessment> assessments= assessmentRepository.getAllAssessmentsByCourseId(cid);
		return assessments.stream().map(a->convertToDTO(a)).collect(Collectors.toList());
	}

}
