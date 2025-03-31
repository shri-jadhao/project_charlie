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
import elearning.project.models.quizmodel.QuestionWrapper;
import elearning.project.models.quizmodel.Quiz;
import elearning.project.models.quizmodel.Response;
import elearning.project.services.quizservices.QuizService;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
	@Autowired
	QuizService service;
	
	@PostMapping
	public ResponseEntity<String> postTheQuestions(@RequestParam String catogery,@RequestParam int questions,@RequestParam String title,@RequestParam Long assessmentid){
		System.out.println("Api called!");
		return service.createQuiz(catogery,questions,title,assessmentid);
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
	@PostMapping("/submit/{id}")
    public ResponseEntity<Double> getSubmit(@PathVariable Long id,@RequestBody List<Response> response,@RequestParam Long studentid){
		return service.getSubmit(id,response,studentid);
	}

}


