package elearning.project.repositories
;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import elearning.project.modelDTO.AssessmentDTO;
import elearning.project.models.Assessment;

public interface AssessmentRepo extends JpaRepository<Assessment, Long> {
       Optional<Assessment> findByName(String name);
       
       @Query("select a from Assessment a where a.course.courseID=:cid")
       public List<Assessment> getAllAssessmentsByCourseId(Long cid);
}
