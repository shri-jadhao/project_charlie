package elearning.project.modelDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubmissionDTO {
       private Long submissionId;
       private double score;
       private Long studentId; 
       private Long assessmentId;
       
}
