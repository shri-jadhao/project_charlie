package elearning.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Submission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long submissionId;
	private Double score;
	
	@ManyToOne
	@JoinColumn(name="studentid")
	private User student;
	
	@ManyToOne
	@JoinColumn(name="assessmentid")
	@JsonIgnoreProperties("submission")
	private Assessment assessment;
	

	
}
