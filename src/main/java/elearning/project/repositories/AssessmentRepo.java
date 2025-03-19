package elearning.project.repositories
;

import org.springframework.data.jpa.repository.JpaRepository;

import elearning.project.models.Assessment;

public interface AssessmentRepo extends JpaRepository<Assessment, Long> {

}
