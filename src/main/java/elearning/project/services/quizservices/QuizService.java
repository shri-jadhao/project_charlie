package elearning.project.services.quizservices;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import elearning.project.exceptions.NotEnrolledException;
import elearning.project.exceptions.QuizIdNotFoundException;
import elearning.project.models.Assessment;
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
import elearning.project.services.SubmissionService;

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

	public ResponseEntity<String> createQuiz(String catogery, int questions, String title, Long id) {
		System.out.println("In the creation of quiz");
		List<Quizquestions> Q = questionsdao.findRandomQuestionsByCatogery(catogery, questions);
		Quiz quiz = new Quiz();
		quiz.setQuestions(Q);
		quiz.setTitle(title);
		quiz.setAssessment((assessRepo.findById(id)).get());
		quizrepo.save(quiz);
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	public List<Quiz> getAllQuiz() {
		return quizrepo.findAll();
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Long id) {
		Optional<Quiz> questions = quizrepo.findById(id);
		List<QuestionWrapper> wrapper = new ArrayList<QuestionWrapper>();
		if (questions.isEmpty()) {
			throw new QuizIdNotFoundException("Id not found Please verify teh id");
		} else {
			List<Quizquestions> quizQs = questions.get().getQuestions();
			for (Quizquestions q : quizQs) {
				wrapper.add(new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(),
						q.getOption3(), q.getOption4()));
			}
		}
		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}

	public ResponseEntity<Double> getSubmit(Long id, List<Response> response, Long studentid) {
		Quiz quiz = quizrepo.findById(id).get();
		Course course = assessRepo.findById(quiz.getAssessment().getAssessmentID()).get().getCourse();
		List<Enrollment> enrolls = course.getEnrollments();
		int k = 0;
		for (Enrollment e : enrolls) {
			if (studentid == e.getStudent().getUserID()) {
				k = 1;
			}
		}
		if (k == 0) {
			throw new NotEnrolledException("User not enrolled in the course!");
		} else {

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
			return new ResponseEntity<>(right, HttpStatus.OK);
		}
	}
}
