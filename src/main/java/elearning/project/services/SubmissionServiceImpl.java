package elearning.project.services;

import elearning.project.modelDTO.SubmissionDTO;
import elearning.project.models.Submission;
import elearning.project.repositories.SubmissionRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private static final Logger logger = LoggerFactory.getLogger(SubmissionServiceImpl.class);

    @Autowired
    private SubmissionRepo submissionRepository;

    @Override
    public Submission createSubmission(Submission submission) {
        logger.info("Creating submission with ID: {}", submission);
        return submissionRepository.save(submission);
    }

    @Override
    public List<SubmissionDTO> getAllSubmissions() {
        logger.info("Fetching all submissions");
        return submissionRepository.findAll().stream().map(sub->convertToDTO(sub)).collect(Collectors.toList());
    }

    @Override
    public Optional<SubmissionDTO> getSubmissionById(Long id) {
        logger.info("Fetching submission with ID: {}", id);
        return submissionRepository.findById(id).map(sub->convertToDTO(sub));
    }

    public SubmissionDTO convertToDTO(Submission submission) {
        return new SubmissionDTO(
                submission.getSubmissionId(),
                submission.getScore(),
                submission.getStudent().getUserID(),  // Extract only Student ID
                submission.getAssessment().getAssessmentID()  // Extract only Assessment ID
        );
    }
}
