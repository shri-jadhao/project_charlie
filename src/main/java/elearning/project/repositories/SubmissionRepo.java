package elearning.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import elearning.project.models.Submission;

public interface SubmissionRepo extends JpaRepository<Submission, Long> {

}
