package elearning.project.services;

import elearning.project.modelDTO.SubmissionDTO;
import elearning.project.models.Submission;
import java.util.List;
import java.util.Optional;

public interface SubmissionService {
    Submission createSubmission(Submission submission);
    List<SubmissionDTO> getAllSubmissions();
    Optional<SubmissionDTO> getSubmissionById(Long id);
}