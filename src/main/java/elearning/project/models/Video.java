package elearning.project.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
	private Long vidoeid;
	private String videourl;
	
	@ManyToOne
	@JoinColumn(name="courseid")
    @JsonIgnoreProperties("video")
	private Course course;

}
