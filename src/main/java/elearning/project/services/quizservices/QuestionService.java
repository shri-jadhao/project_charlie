package elearning.project.services.quizservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elearning.project.models.quizmodel.Quizquestions;
import elearning.project.repositories.QuestionsRepo;


@Service
public class QuestionService {
	@Autowired
	QuestionsRepo repo;

	public List<Quizquestions> getAllquestions() {
		return repo.findAll();

	}

	public void uploadquestions(Quizquestions q) {
		  repo.save(q);
	}

	public List<Quizquestions> getQuestionsByCatogery(String catogery) {
		   return repo.findByCategory(catogery);
	}
}
