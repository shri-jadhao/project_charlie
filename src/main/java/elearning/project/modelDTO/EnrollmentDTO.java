package elearning.project.modelDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnrollmentDTO {
       private Long enrollmentId;
       private double progress;
       private Long courseId;
       private Long studentId;
}
