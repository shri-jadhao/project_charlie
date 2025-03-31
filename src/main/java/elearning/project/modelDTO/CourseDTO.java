package elearning.project.modelDTO;

import java.util.List;

import elearning.project.models.Video;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseDTO {
	private Long courseID;
	private String title;
	private String description;
	private String contentURL;
	private Long instructorID;	
	private List<Long> videoIDs;
}
