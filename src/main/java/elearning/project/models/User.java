package elearning.project.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userID;

	private String username;
	@Enumerated(EnumType.STRING)
	@NotEmpty
	private Role role;

	@OneToMany(mappedBy = "instructor")
	@JsonIgnore
	List<Course> course;

	@Column(unique = true)
	@Email(message="Email must not be null")
	private String email;
    
//	^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$ 
//	1.atleast one char,one didgit cap and small togeher 8 
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters long and contain at least one letter and one digit")
	private String password;

	public enum Role {
		STUDENT, INSTRUCTOR
	}
}
