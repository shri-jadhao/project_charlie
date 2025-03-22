package elearning.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import elearning.project.models.quizmodel.Quiz;
@Repository
public interface QuizRepo extends JpaRepository<Quiz, Long> {

}
