 package elearning.project.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import elearning.project.modelDTO.QuizDTO;
import elearning.project.modelDTO.ResultDTO;
import elearning.project.models.quizmodel.QuestionWrapper;
import elearning.project.models.quizmodel.Quiz;
import elearning.project.models.quizmodel.Response;
import elearning.project.services.quizservices.QuizService;
import lombok.Data;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
	@Autowired
	QuizService service;
	
	@PreAuthorize("hasRole('INSTRUCTOR')")
	@PostMapping
	public ResponseEntity<List<QuestionWrapper>> postTheQuestions(@RequestBody QuizDTO quiz){
		return service.createQuiz(quiz.getCategory(),quiz.getQuestions(),quiz.getTitle(),quiz.getAssessmentId());
	}
//	@PreAuthorize("hasRole('INSTRUCTOR')")
	@GetMapping
	public ResponseEntity<List<QuizDTO>> getAllQuiz(){
		return service.getAllQuiz();
	}
	@GetMapping("{id}")
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Long id){
		 return service.getQuizQuestions(id);
		
	}
	@PostMapping("/{id}/submit")
    public ResponseEntity<ResultDTO> getSubmit(@PathVariable Long id,@RequestBody List<Response> response){
		return service.getSubmit(id,response);
	}
	


}


