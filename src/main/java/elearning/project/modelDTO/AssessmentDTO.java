package elearning.project.modelDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssessmentDTO {
       private Long assessmentId;
       private String name;
       private Long courseId;
       private String role;
       private int maxScore;
       private List<SubmissionDTO> submissions;
       
       
}
