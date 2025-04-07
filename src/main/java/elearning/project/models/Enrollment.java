package elearning.project.models;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long enrollmentId;

	private Double progress;

	@ManyToOne
	@JoinColumn(name = "courseid")
	Course course;
	
    @ManyToOne
    @JoinColumn(name="studentid")
    User student;
}
