 package elearning.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import elearning.project.models.quizmodel.Quizquestions;
import elearning.project.services.quizservices.QuestionService;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/questions")
public class QuestionsController {
	@Autowired
	QuestionService service;
	@GetMapping("")
	public ResponseEntity<List<Quizquestions>> getAllquestions(){
		 if(service.getAllquestions()!=null) {
			 return new ResponseEntity<>(service.getAllquestions(),HttpStatus.OK);
		 }
		 else {
			 return new ResponseEntity<>(new ArrayList(),HttpStatus.BAD_REQUEST);
		 }
	}
	@PostMapping("")
	public void uploadquestions(@RequestBody List<Quizquestions> question) {
		for(Quizquestions q:question ) {
			   service.uploadquestions(q);
		}

		
	}
	@GetMapping("/{catogery}")
	public List<Quizquestions> getQuestionsbyCatogery(@PathVariable String catogery) {
		return service.getQuestionsByCatogery(catogery);
	}

}
