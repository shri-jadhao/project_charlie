package elearning.project.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import elearning.project.models.quizmodel.Quiz;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assessment {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long assessmentID;

	@ManyToOne
	@JoinColumn(name="courseid")
	@JsonIgnoreProperties("assessment")
	Course course;
	
	private Type role;
	private int maxscore;
	
	@OneToMany(mappedBy="assessment",cascade = CascadeType.ALL)
	@JsonIgnoreProperties("assessment")
	private List<Submission> submission;
	
	
	
	public enum Type {
		QUIZ, ASSIGNMENT
	}
}