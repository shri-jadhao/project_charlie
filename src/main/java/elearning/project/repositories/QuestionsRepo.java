package elearning.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import elearning.project.models.quizmodel.Quizquestions;



@Repository
public interface QuestionsRepo extends JpaRepository<Quizquestions, Integer> {

	List<Quizquestions> findByCategory(String catogery);  //-> intelligent JPA
	@Query(value="select * from quizquestions q where q.category=:catogery order by rand() limit questions",nativeQuery=true)
	List<Quizquestions> findRandomQuestionsByCatogery(String catogery, int questions);
}
