package elearning.project.services.quizservices;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import elearning.project.exceptions.NotEnrolledException;
import elearning.project.exceptions.QuizException;
import elearning.project.exceptions.QuizIdNotFoundException;
import elearning.project.exceptions.ResourceIdNotFoundException;
import elearning.project.modelDTO.CourseDTO;
import elearning.project.modelDTO.QuizDTO;
import elearning.project.modelDTO.ResultDTO;
import elearning.project.models.Course;
import elearning.project.models.Enrollment;
import elearning.project.models.Submission;
import elearning.project.models.quizmodel.QuestionWrapper;
import elearning.project.models.quizmodel.Quiz;
import elearning.project.models.quizmodel.Quizquestions;
import elearning.project.models.quizmodel.Response;
import elearning.project.repositories.AssessmentRepo;
import elearning.project.repositories.QuestionsRepo;
import elearning.project.repositories.QuizRepo;
import elearning.project.repositories.UserRepo;
import elearning.project.securitypriciples.UserPrincipals;
import elearning.project.services.SubmissionService;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Service
public class QuizService {
	@Autowired
	QuizRepo quizrepo;
	@Autowired
	QuestionsRepo questionsdao;
	@Autowired
	AssessmentRepo assessRepo;
	@Autowired
	UserRepo userrepo;
	@Autowired
	SubmissionService submission;

	public ResponseEntity<List<QuestionWrapper>> createQuiz(String catogery, int questions, String title, Long id) {
		System.out.println("In the creation of quiz");
		if(questionsdao.findByCategory(catogery).isEmpty()) {
			throw new QuizException("Given catogery not found!");
		}
		else if(assessRepo.findById(id).isEmpty()) {
			throw new ResourceIdNotFoundException("The assessment is not present/deleted please choose other assessments");
		}
		else {
		    int count =questionsdao.findNumberOfQuestionsByCategory(catogery);
		    if(questions>count) {
		    	throw new QuizException("Number questions is too large to create quiz!");
		    }
		}
		
		List<Quizquestions> Q = questionsdao.findRandomQuestionsByCatogery(catogery, questions);
		System.out.println("Hello bro insidQs genrated!");
		Quiz quiz = new Quiz();
		quiz.setQuestions(Q);
		quiz.setTitle(title);
		quiz.setAssessment((assessRepo.findById(id)).get());
		quizrepo.save(quiz);
		List<QuestionWrapper> questionWrappers=getQuizQuestions(quiz.getId()).getBody();
		return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
	}

	public ResponseEntity<List<QuizDTO>> getAllQuiz() {
		List<QuizDTO> quizdto=quizrepo.findAll().stream().map(q->convertToDTO(q)).collect(Collectors.toList());
		return new ResponseEntity<>(quizdto,HttpStatus.OK);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Long id) {
		Optional<Quiz> questions = quizrepo.findById(id);
		List<QuestionWrapper> wrapper = new ArrayList<QuestionWrapper>();
		if (questions.isEmpty()) {
			throw new QuizIdNotFoundException("Id not found Please verify the id");
		} else {
			List<Quizquestions> quizQs = questions.get().getQuestions();
			for (Quizquestions q : quizQs) {
				wrapper.add(new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(),
						q.getOption3(), q.getOption4()));
			}
		}
		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}

	public ResponseEntity<ResultDTO> getSubmit(Long id, List<Response> response) {
		if(quizrepo.findById(id).isEmpty()) {
			throw new QuizIdNotFoundException("quiz doesnt exists!");
		}
		Quiz quiz = quizrepo.findById(id).get();
		Course course = assessRepo.findById(quiz.getAssessment().getAssessmentID()).get().getCourse();
		List<Enrollment> enrolls = course.getEnrollments();
		int enrolled = 0;
		Long studentid=getLoggerDetails().getUser().getUserID();
		for (Enrollment e : enrolls) {
			if (studentid == e.getStudent().getUserID()) {
				enrolled = 1;
			}				
		}
		int length=response.size();
		if (enrolled == 0) {
			throw new NotEnrolledException("User not enrolled in the course!");
		} 
		else {
			Submission s = new Submission();
			s.setAssessment((assessRepo.findById(quiz.getAssessment().getAssessmentID()).get()));
			s.setStudent(userrepo.findById(studentid).get());
			List<Quizquestions> questions = quiz.getQuestions();
			double right = 0;
			int i = 0;
			for (Response res : response) {
				System.err.println("hello in response");
				if (res.getResponse().equals(questions.get(i).getRightAnswer())) {
					right++;
				}
				i++;
			}
			s.setScore(right);
			submission.createSubmission(s);
			return new ResponseEntity<>(new ResultDTO(right,((right/length)*100)), HttpStatus.OK);
		}
	}
	public QuizDTO convertToDTO(Quiz quiz) {
	    return new QuizDTO(
	            quiz.getId(),
	            quiz.getTitle(),
	            quiz.getQuestions().stream().map(q->q.getId()).collect(Collectors.toList()), // Extract only question IDs
	            quiz.getAssessment().getAssessmentID(),  // Extract only Assessment ID
	            quiz.getQuestions().get(0).getCategory(),
	            quiz.getQuestions().size()
	    );
	}	
	public UserPrincipals getLoggerDetails() {
		UserPrincipals userdetails = (UserPrincipals) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return userdetails;
	}

}
