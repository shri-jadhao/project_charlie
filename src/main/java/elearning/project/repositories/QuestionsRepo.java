package elearning.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import elearning.project.models.quizmodel.Quizquestions;



@Repository
public interface QuestionsRepo extends JpaRepository<Quizquestions, Integer> {
	
	List<Quizquestions> findByCategory(String catogery);  //-> power of JPA
	
	@Query(value="select * from quizquestions q where q.category=:catogery order by rand() limit questions",nativeQuery=true)
	List<Quizquestions> findRandomQuestionsByCatogery(String catogery, @Param("questions") int questions);
	
	@Query(value="select count(*) from quizquestions q where q.category=:category",nativeQuery=true)
	int findNumberOfQuestionsByCategory(String category);
}
