package elearning.project.services;

import elearning.project.models.Submission;
import java.util.List;
import java.util.Optional;

public interface SubmissionService {
    Submission createSubmission(Submission submission);
    List<Submission> getAllSubmissions();
    Optional<Submission> getSubmissionById(Long id);
    Submission updateSubmission(Long id, Submission submissionDetails);
    void deleteSubmission(Long id);
}