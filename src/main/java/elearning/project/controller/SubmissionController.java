package elearning.project.controller;

import elearning.project.models.Submission;
import elearning.project.services.SubmissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/submission")
public class SubmissionController {

	@Autowired
	private SubmissionService submissionService;

	@PostMapping("")
	public ResponseEntity<Submission> createSubmission(@RequestBody Submission submission) {
		Submission createdSubmission = submissionService.createSubmission(submission);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdSubmission);
	}

	@GetMapping("")
	public ResponseEntity<List<Submission>> getAllSubmissions() {
		List<Submission> submissions = submissionService.getAllSubmissions();
		return ResponseEntity.ok(submissions);
	}

}