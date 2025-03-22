package elearning.project.services;

import elearning.project.models.Submission;
import elearning.project.repositories.SubmissionRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

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
    public List<Submission> getAllSubmissions() {
        logger.info("Fetching all submissions");
        return submissionRepository.findAll();
    }

    @Override
    public Optional<Submission> getSubmissionById(Long id) {
        logger.info("Fetching submission with ID: {}", id);
        return submissionRepository.findById(id);
    }

    @Override
    public void deleteSubmission(Long id) {
        logger.info("Deleting submission with ID: {}", id);
        submissionRepository.deleteById(id);
    }

    @Override
    public Submission updateSubmission(Long id, Submission submissionDetails) {
        logger.info("Updating submission with ID: {}", id);
        // Implementation for updating the submission
        return null;
    }
}
